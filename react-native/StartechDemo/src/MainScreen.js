import React, {Component} from 'react';
import {StyleSheet, Text, TextInput, TouchableOpacity, View} from 'react-native';

export default class MainScreen extends Component {

  constructor(props) {
    super(props);
    this.state ={
      name: 'Anonymous',
      channel: 'Main Channel'
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.row}>
          <Text style={styles.label}>
            Your name:
          </Text>

          <TextInput
            style={styles.input}
            onChangeText={name => this.setState({name})}
            value={this.state.name}
          />
        </View>

        <View style={styles.row}>
          <Text style={styles.label}>
            Channel:
          </Text>


          <TextInput
            style={styles.input}
            onChangeText={channel => this.setState({channel})}
            value={this.state.channel}
          />
        </View>

        <TouchableOpacity onPress={this.startChat.bind(this)}>
          <Text style={styles.join}>
            Join Channel
          </Text>
        </TouchableOpacity>

      </View>
    );
  }

  startChat() {
    this.props.navigation.navigate('ChatScreen', {...this.state})
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  row: {
    flexDirection: 'row'
  },
  join: {
    color: 'blue',
    fontSize: 25,
    textAlign: 'center',
    marginTop: 20
  },
  label: {
    fontSize: 20,
    textAlign: 'right',
    margin: 10,
    width: 100
  },
  input: {
    height: 40,
    width: 200,
    paddingLeft: 5,
    fontSize: 20,
    color: 'teal',
    borderBottomWidth: 1,
    borderColor: 'grey',
    alignSelf: 'center'
  }
});
