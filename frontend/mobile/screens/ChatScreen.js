import React, { useState, useEffect, useCallback } from 'react';
import { View, Image, Text, Button, StyleSheet } from 'react-native';
import { Bubble, GiftedChat, Send, InputToolbar, Actions, Composer, MessageText, Avatar } from 'react-native-gifted-chat';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import emojiUtils from 'emoji-utils'
import SMessage from '../utils/SMessage'

import ImagePicker from 'react-native-image-crop-picker';

const ChatScreen = ({ title }) => {
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    setMessages([
      {
        _id: 1,
        text: 'Hi',
        createdAt: new Date(),
        user: {
          _id: 1,
          name: title.userName,
          avatar: title.userImg,
        },
      },
      {
        _id: 0,
        text: 'Hello world',
        createdAt: new Date(),
        user: {
          _id: 0,
          name: 'Phuoc Quoc',
          avatar: 'https://shorturl.at/cdeC8',
        },
      },
      {
        _id: 2,
        text: '2222',
        createdAt: new Date(),
        user: {
          _id: 0,
          name: 'Phuoc Quoc',
          avatar: 'https://shorturl.at/cdeC8',
        },
      },
      {
        _id: 2,
        text: 'image',
        image: "file:///storage/emulated/0/Android/data/com.socialapp/files/Pictures/0ae91426-8c10-41bd-b5bd-7b1d575ed080.jpg",
        createdAt: new Date(),
        user: {
          _id: 0,
          name: 'Phuoc Quoc',
          avatar: 'https://shorturl.at/cdeC8',
        }
      }

    ]);
  }, []);


  const onSend = useCallback((messages = []) => {
    setMessages((previousMessages) =>
      GiftedChat.append(previousMessages, messages),
    );
  }, []);

  const renderMessage = (props) => {
    const {
      currentMessage: { text: currText },
    } = props

    let messageTextStyle

    if (currText && emojiUtils.isPureEmojiString(currText)) {
      messageTextStyle = {
        fontSize: 28,
        lineHeight: Platform.OS === 'android' ? 34 : 30,
      }
    }

    return <SMessage {...props} messageTextStyle={messageTextStyle} />
  }

  const renderSend = (props) => {
    return (
      <Send {...props}
        disabled={!props.text}
        containerStyle={{
          width: 44,
          height: 44,
          alignItems: 'center',
          justifyContent: 'center',
          marginHorizontal: 4,
        }}>
        <View>
          <MaterialCommunityIcons
            name="send-circle"
            style={{ marginBottom: 5, marginRight: 5 }}
            size={32}
            color="#2e64e5"
          />
        </View>
      </Send>
    );
  };

  const renderBubble = (props) => {
    return (
      <Bubble
        {...props}
        textStyle={{
          left: {
            color: 'black',
          },
          right: {
            color: 'black',
          },
        }
        }
      />
    );
  };

  const scrollToBottomComponent = () => {
    return (
      <FontAwesome name='angle-double-down' size={22} color='#333' />
    );
  }

  const renderInputToolbar = (props) => (
    <InputToolbar
      {...props}
      containerStyle={{
        backgroundColor: '#222B45',
        paddingTop: 6,
      }}
      primaryStyle={{ alignItems: 'center' }}
    />
  );

  const renderActions = (props) => (
    <Actions
      {...props}
      containerStyle={{
        width: 44,
        height: 44,
        alignItems: 'center',
        justifyContent: 'center',
        marginLeft: 4,
        marginRight: 4,
        marginBottom: 0,
      }}
      icon={() => (

        <FontAwesome name='plus-square' size={22} color='#fff' />
      )}
      options={{
        'Choose From Library': () => {

          ImagePicker.openPicker({
            width: 300,
            height: 300,
            cropping: true,
            compressImageQuality: 0.7,
          }).then((image) => {
            const imageUri = Platform.OS === 'ios' ? image.sourceURL : image.path;
            onSend({ image: imageUri });
            console.log(messages)
          });
        },
        Cancel: () => {
          console.log('Cancel');
        },
      }}
      optionTintColor="#222B45"
    />
  );

  const renderComposer = (props) => (
    <Composer
      {...props}
      textInputStyle={{
        color: '#222B45',
        backgroundColor: '#EDF1F7',
        borderWidth: 1,
        borderRadius: 5,
        borderColor: '#E4E9F2',
        paddingTop: 8.5,
        paddingHorizontal: 12,
        marginLeft: 0,
      }}
    />
  );
  const renderAvatar = (props) => (
    <Avatar
      {...props}
      containerStyle={{ left: { borderColor: 'red' }, right: {} }}
      imageStyle={{ left: { borderColor: 'blue' }, right: {} }}
    />
  );
  const renderSystemMessage = (props) => (
    <SystemMessage
      {...props}
      containerStyle={{ backgroundColor: 'pink' }}
      wrapperStyle={{ borderWidth: 10, borderColor: 'white' }}
      textStyle={{ color: 'crimson', fontWeight: '900' }}
    />
  );
  const renderMessageText = (props) => (
    <MessageText
      {...props}
      containerStyle={{
        left: { backgroundColor: 'yellow' },
        right: { backgroundColor: 'purple' },
      }}
      textStyle={{
        left: { color: 'red' },
        right: { color: 'green' },
      }}
      linkStyle={{
        left: { color: 'orange' },
        right: { color: 'orange' },
      }}
      customTextStyle={{ fontSize: 24, lineHeight: 24 }}
    />
  );


  return (
    <GiftedChat
      messages={messages}
      onSend={(messages) => onSend(messages)}
      user={{
        _id: 0,
        name: 'React Native',
        avatar: 'https://shorturl.at/cdeC8',
      }}
      alwaysShowSend
      scrollToBottom
      showUserAvatar
      renderAvatarOnTop
      renderUsernameOnMessage
      renderBubble={renderBubble}
      renderSend={renderSend}
      scrollToBottom
      scrollToBottomComponent={scrollToBottomComponent}
      renderMessage={renderMessage}
      renderInputToolbar={renderInputToolbar}
      renderActions={renderActions}
      renderComposer={renderComposer}
      renderComposer={renderComposer}
      renderSend={renderSend}
      // renderAvatar={renderAvatar}
      renderBubble={renderBubble}
      renderSystemMessage={renderSystemMessage}
      isCustomViewBottom
      parsePatterns={(linkStyle) => [
        {
          pattern: /#(\w+)/,
          style: linkStyle,
          onPress: (tag) => console.log(`Pressed on hashtag: ${tag}`),
        },
      ]}
    />
  );
};

export default ChatScreen;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
