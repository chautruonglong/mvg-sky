import React from 'react';
import { useState, useEffect, useContext, useCallback, useRef } from 'react';
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
import socketIOClient from "socket.io-client";
import apiRequest from '../utils/apiRequest';
import { AuthContext } from '../navigation/AuthProvider';

const MessagesScreen = ({ navigation }) => {
  const { user, myrooms } = useContext(AuthContext);
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

  const avtDefault = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWbS3I9NbSTEsVOomPr66VVL38-x1RLajLZQ&usqp=CAU'

  const fetchRoom = async () => {
    const rooms = await apiRequest.get(`/rooms?accountId=${user.account.id}`)
    setMessages(rooms)
  }

  useEffect(() => {
    fetchRoom()
  }, [])

  return (
    <Container>
      <FlatList
        data={messages}
        keyExtractor={item => item.id}
        renderItem={({ item }) => (
          <Card onPress={() => navigation.navigate('Chat', { userName: item.name, messageText: '', userImg: item?.avatar || avtDefault, roomId: item.id })}>
            <UserInfo>
              <UserImgWrapper>
                <UserImg source={{
                  uri: item?.avatar || avtDefault
                }} />
                {/* <UserImg source={{ uri: item.userImg }} /> */}
              </UserImgWrapper>
              <TextSection>
                <UserInfoText>
                  <UserName>{item.name}</UserName>
                  <PostTime>{''}</PostTime>
                </UserInfoText>
                <MessageText
                  numberOfLines={1}
                >{''}</MessageText>
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
    justifyContent: 'center',
  },
});
