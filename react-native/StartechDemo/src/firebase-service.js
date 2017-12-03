import * as firebase from 'firebase';
import _ from 'lodash';
import config from '../config';

const firebaseApp = firebase.initializeApp(config);
let db;

export const subscribe = ({channel, callback}) => {
  db = firebaseApp.database().ref('/channels/' + channel);

  db.on('value', snap => {
    const messages = [];
    snap.forEach(item => {
      messages.push(item.val());
    });

    _.sortBy(messages, 'timestamp');

    callback(messages);
  });

};

export const addChild = data => db.push(data);
