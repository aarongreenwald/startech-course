import React, {Component} from 'react';
import {StyleSheet, Text, TextInput, TouchableOpacity, View} from 'react-native';

export default class MainScreen extends Component {

  constructor(props) {
    super(props);

    this.state = {
      name: 'Anonymous',
      channel: 'Main Channel'
    }
  }

  openChat() {
    this.props.navigation.navigate('ChatScreen', {
      name: this.state.name,
      channel: this.state.channel
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <TextInput
          style={styles.input}
          onChangeText={(name) => this.setState({name})}
          value={this.state.name}
        />
        <TextInput
          style={styles.input}
          onChangeText={(channel) => this.setState({channel})}
          value={this.state.channel}
        />
        <TouchableOpacity
          onPress={this.openChat.bind(this)}
          style={styles.button}>
          <Text>Join Channel</Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {flex: 1, alignItems: 'center', justifyContent: 'center'},
  input: {height: 40, width: 100, borderColor: 'gray', borderWidth: 1},
  button: {
    backgroundColor: 'blue'
  }
});