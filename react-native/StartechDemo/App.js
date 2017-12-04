import MainScreen from './src/MainScreen'
import ChatScreen from './src/ChatScreen'

import {StackNavigator} from 'react-navigation';

export default StackNavigator({
  Main: {screen: MainScreen},
  ChatScreen: {screen: ChatScreen}
});
