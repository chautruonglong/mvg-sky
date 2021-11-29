import React from 'react';
import { useState, useEffect, useCallback } from 'react';
import { View, Text, Button, StyleSheet, FlatList } from 'react-native';
import {
  Container,
  Card,
  UserInfo,
  UserImgWrapper,
  UserImg,
  UserInfoText,
  UserName,
  PostTime,
  MessageText,
  TextSection,
} from '../styles/MessageStyles';


const MessagesScreen = ({ navigation }) => {
  const [messages, setMessages] = useState([]);

  // const fetchPosts = async () => {
  //   try {
  //     const response = await fetch('http://192.168.0.110:2222/contact');
  //     const mess = await response.json();
  //     setMessages(mess);
  //   }
  //   catch (error) {
  //     console.log(error)
  //   }
  // }
  // useEffect(() => { fetchPosts() }, [])
  useEffect(() => {
    setMessages([
      {
        id: '1',
        userName: 'Vien Mai',
        userImg: 'https://shorturl.at/djrOT',
        messageTime: '4 mins ago',
        messageText:
          'Hey there, this is my test for a post of my social app in React Native.',
      },
      {
        id: '3',
        userName: 'The Tue',
        userImg: 'https://shorturl.at/nBMRY',
        messageTime: '1 hours ago',
        messageText:
          'Hey there, this is my test for a post of my social app in React Native.',
      },
    ]);
  }, []);
  return (
    <Container>
      <FlatList
        data={messages}
        keyExtractor={item => item.id}
        renderItem={({ item }) => (
          <Card onPress={() => navigation.navigate('Chat', { userName: item.userName, messageText: item.messageText, userImg: item.userImg })}>
            <UserInfo>
              <UserImgWrapper>
                <UserImg source={{
                  uri: item.userImg,
                }} />
                {/* <UserImg source={{ uri: item.userImg }} /> */}
              </UserImgWrapper>
              <TextSection>
                <UserInfoText>
                  <UserName>{item.userName}</UserName>
                  <PostTime>{item.messageTime}</PostTime>
                </UserInfoText>
                <MessageText
                  numberOfLines={1}
                >{item.messageText}</MessageText>
              </TextSection>
            </UserInfo>
          </Card>
        )}
      />
    </Container>
  );
};

export default MessagesScreen;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center'
  },
});
