import React, {Component} from 'react';
import {StyleSheet, Text, TextInput, TouchableOpacity, View, ScrollView} from 'react-native';
import * as firebaseService from './firebase-service';

export default class ChatScreen extends Component {

  constructor(props) {
    super(props);

    this.state = {
      messages: []
    };

    firebaseService.subscribe({
      channel: props.navigation.state.params.channel,
      callback: this.setMessages.bind(this)
    })
  }

  setMessages(messages) {
    this.setState({messages});
  }

  send() {
    const messageDraft = this.state.messageDraft;
    firebaseService.addChild({
      sender: this.props.navigation.state.params.name,
      text: messageDraft,
      timestamp: (new Date()).getTime()
    });
    this.setState({messageDraft: ''});
  }

  render() {
    return (
      <View style={styles.container}>
        <ScrollView>
          {
            this.state.messages.map((message, i) =>
              <Row message={message} key={i}/>
            )
          }
        </ScrollView>
        <TextInput
          style={styles.input}
          onChangeText={(messageDraft) => this.setState({messageDraft})}
          value={this.state.messageDraft}
        />
        <TouchableOpacity onPress={this.send.bind(this)}>
          <Text>Send</Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const Row = ({message}) => {
 return (
   <View style={styles.rowContainer}>
     <Text style={styles.sender}>{message.sender}</Text>
     <Text style={styles.messageText}>{message.text}</Text>
   </View>
 )
}

const styles = StyleSheet.create({
  container: {flex: 1, alignItems: 'center', justifyContent: 'center'},
  input: {height: 40, width: 100, borderColor: 'gray', borderWidth: 1},
  button: {
    backgroundColor: 'blue'
  },
  rowContainer: {
    padding: 10
  },
  sender: {
    color: 'green'
  },
  messageText: {
    color: '#1385FA'
  }
});