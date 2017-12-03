import ChatScreen from './src/ChatScreen';
import MainScreen from './src/MainScreen'

import {StackNavigator} from 'react-navigation';

export default StackNavigator({
  Main: {screen: MainScreen},
  ChatScreen: {screen: ChatScreen},
});
