import React, {Component} from 'react';
import {View, ListView, Text, TextInput, TouchableOpacity, StyleSheet} from 'react-native';
import * as firebaseService from './firebase-service';

export default class ChatScreen extends Component{

  static navigationOptions = ({navigation}) => ({
    title: navigation.state.params.name,
  });

  constructor(props) {
    super(props);
    this.state={
      ds: new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2}),
      messageDraft: ''
    };
    this.subscribeToFirebase();
  }

  subscribeToFirebase() {
    const {name, channel} = this.props.navigation.state.params;
    this.name = name;
    firebaseService.subscribe({
      channel,
      callback: messages => {
        this.setState({
          ds: this.state.ds.cloneWithRows(messages)
        });
      }
    });
  }

  send() {
    firebaseService.addChild({
      sender: this.name,
      text: this.state.messageDraft,
      timestamp: Date.now()
    });
    this.setState({messageDraft:''})
  }

  render() {
    return (
      <View style={{flex: 1}}>
        <ListView
          style={{flex: 1}}
          dataSource={this.state.ds}
          renderRow={row => <Row {...row} />}
          enableEmptySections={true}
        />
        <View style={styles.row}>
          <TextInput
            style={styles.input}
            onChangeText={messageDraft => this.setState({messageDraft})}
            value={this.state.messageDraft}
          />
          <TouchableOpacity onPress={this.send.bind(this)}>
            <Text style={styles.send}>
              Send
            </Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }
}

const Row = ({sender, text}) => (
  <View style={styles.messageRow}>
    <Text style={styles.sender}>
      {sender}
    </Text>

    <Text style={styles.message}>
      {text}
    </Text>
  </View>
);

const styles = StyleSheet.create({
  row: {
    flexDirection: 'row'
  },
  input: {
    flex: 1,
    height: 40,
    borderWidth: 1,
    borderColor: 'grey',
    alignSelf: 'center'
  },
  send: {
    color: 'blue',
    padding: 10
  },
  sender: {
    fontSize: 16,
    color: 'green'
  },
  message: {
    fontSize: 20
  },
  messageRow: {
    padding: 10
  }
});
