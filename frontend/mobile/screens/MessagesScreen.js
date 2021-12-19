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
import moment from "moment";
import HTMLView from "react-native-htmlview";
import socketIOClient from "socket.io-client";
import apiRequest from '../utils/apiRequest';
import { AuthContext } from '../navigation/AuthProvider';
import ActionButton from 'react-native-action-button';
import NewWindow from 'react-new-window'
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

  // const fetchRoom = async () => {
  //   const rooms = await apiRequest.get(`/rooms?accountId=${user.account.id}`)
  //   setMessages(rooms)
  // }

  // useEffect(() => {
  //   fetchRoom()
  // }, [])
  return (
    <Container>
      <FlatList
        data={myrooms}
        keyExtractor={item => item.id}
        renderItem={({ item }) => (
          <Card onPress={() => navigation.navigate('Chat', { userName: item.name, messageText: '', userImg: item?.avatar || avtDefault, roomId: item.id })}>
            <UserInfo>
              <UserImgWrapper>
                {() => {
                  console.log(item)
                }}
                <UserImg source={{
                  uri: item?.avatar ? 'http://api.mvg-sky.com' + item?.avatar : avtDefault
                }} />
                {/* <UserImg source={{ uri: item.userImg }} /> */}
              </UserImgWrapper>
              <TextSection>
                <UserInfoText>
                  <UserName>{item.name}</UserName>
                  <PostTime>{moment(item?.latestMessage?.createdAt).fromNow()}</PostTime>
                </UserInfoText>
                {/* <MessageText
                  numberOfLines={1}
                >{}</MessageText> */}
                <HTMLView textComponentProps={{ style: { color: 'orange' } }} value={item?.latestMessage?.content} stylesheet={styles.message} />
              </TextSection>
            </UserInfo>
          </Card>
        )}
      />
      <ActionButton
        buttonColor="#2e64e5"
        title="Create Room Screen"
        onPress={() => navigation.navigate('CreateRoomScreen')}>
      </ActionButton>
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
  message: {
    color: "#fff"
  }
});
