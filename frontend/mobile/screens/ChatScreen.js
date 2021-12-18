import React, { memo, useRef, useState, useContext, useEffect, useCallback } from 'react';
import { AuthContext } from '../navigation/AuthProvider';
import Colors from "./Colors";
import Toast from 'react-native-toast-message';
import { Animated, FlatList, TextInput, Image, ListItem, View, Vibration, KeyboardAvoidingView, Text, Button, StyleSheet, TouchableOpacity, ScrollView } from 'react-native';
import { GiftedChat, Send, InputToolbar, Actions, Composer, MessageText, Avatar } from 'react-native-gifted-chat';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import MaterialIcons from 'react-native-vector-icons/MaterialIcons';
import Entypo from 'react-native-vector-icons/Entypo';
import Fontisto from 'react-native-vector-icons/Fontisto';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5';
import emojiUtils from 'emoji-utils'
import SMessage from '../utils/SMessage'
import { SwipeRow } from 'react-native-swipe-list-view';
import ImagePicker from 'react-native-image-crop-picker';
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import ChatMessage from '../components/ChatMessage';
import apiRequest from '../utils/apiRequest';
import moment from "moment";
import DocumentPicker from 'react-native-document-picker'
import EmojiSelector, { Categories } from "react-native-emoji-selector";
import HTMLView from "react-native-htmlview";

import {
  actions,
  RichEditor,
  RichToolbar,
} from "react-native-pell-rich-editor";
// const sockJS = new SockJS('http://api.mvg-sky.com/api/chats/ws');
// const stompClient = Stomp.over(sockJS);
// let isConnected = false;
// stompClient.connect(
//   {},
//   () => {
//     isConnected = true;
//   },
//   (error) => {
//     console.log(error);
//   }
// );
const ChatScreen = ({ title }) => {
  const { stompClient } = useContext(AuthContext);
  const { user, profile, chats, iduser } = useContext(AuthContext)
  const yourRef = useRef(null);
  const [message, setMessage] = useState('');
  const [messages, setMessages] = useState([]);
  const [remessage, setRemessage] = useState('');
  const [showEmojiPicker, setShowEmojiPicker] = useState(false)
  const [heightValue, setHeightValue] = useState(400)
  const chat = useRef();
  const [isShowEmoj, setIsShowEmoj] = useState(false)
  const [article, setArticle] = useState("");
  const RichText = useRef(); //reference to the RichEditor component



  useEffect(() => {
    fetchRoom()
  }, []);



  const fetchRoom = async () => {
    if (title.roomId) {
      // console.log(title.roomId)
      const values = await apiRequest.get(`/messages?roomId=${title.roomId}&limit=15`)
      setMessages(values.reverse())
    }
  }

  function editorInitializedCallback() {
    RichText.current?.registerToolbar(function (items) {
      // items contain all the actions that are currently active
      // console.log(
      //   items
      // );
    });
  }
  useEffect(() => {
    if (chats === null) {
    }
    else {

      if (chats.accountId === user?.account?.id) {
        console.log("aaaaaaaaaaaaaaaaaaaaaaaaa")
      }
      else {


        setMessages([...messages, chats])
      }
    }
  }, [chats]);

  // useEffect(() => {

  //   setMessages([...messages, remessage])
  // }, [remessage]);

  // useEffect(() => {
  //   if (isConnected === true) {
  //     stompClient.subscribe(
  //       `/room/${title.roomId}`,
  //       (payload) => {
  //         const chatMessage = {
  //           accountId: JSON.parse(payload.body).data.accountId,
  //           content: JSON.parse(payload.body).data.content,
  //           threadId: null,
  //           type: "TEXT",
  //           delay: 0
  //         }
  //         setRemessage(chatMessage)
  //       }
  //     );
  //   }
  // }, [isConnected])


  const onSend = () => {
    const chatMessage = {
      accountId: user?.account?.id,
      content: message,
      threadId: null,
      type: "TEXT",
      delay: 0
    }
    stompClient.send(`/chat/send-message/${title.roomId}`, JSON.stringify(chatMessage), {},)
    setMessages([...messages, chatMessage])
    setMessage("")
    RichText.current.setContentHTML("")
  };

  const handleCamera = () => (
    ImagePicker.openPicker({
      width: 300,
      height: 300,
      cropping: true,
      compressImageQuality: 0.7,
    }).then((image) => {
      const imageUri = Platform.OS === 'ios' ? image.sourceURL : image.path;
      const chatMessage = {
        accountId: user?.account?.id,
        content: imageUri,
        threadId: null,
        type: "URL",
        delay: 0
      }
      // stompClient.send(`/chat/send-message/${title.roomId}`, JSON.stringify(chatMessage), {},)
      setMessages([...messages, chatMessage])
      // console.log(imageUri);
    })

  );
  const handleSendFile = async () => {
    try {
      const pickerResult = await DocumentPicker.pickSingle({
        type: [DocumentPicker.types.allFiles],
      })
    } catch (e) {
      console.log(e)
    }
  }
  const onMicrophonePress = () => {
    console.warn('Microphone')
  }
  const isMyMessage = (item) => {
    return item.accountId === user.account.id;
  }
  const onPress = () => {
    console.log(message)
    if (!message) {
      onMicrophonePress();
    } else {
      onSend();
    }
  }

  return (
    <View style={styles.container}>
      <FlatList
        ref={yourRef}
        onContentSizeChange={() => yourRef.current.scrollToEnd()}
        onLayout={() => yourRef.current.scrollToEnd()}
        style={{ width: '100%' }}
        data={messages}
        renderItem={({ item }) =>
          <View style={styles.container1}>
            <View style={[
              styles.messageBox, {
                backgroundColor: isMyMessage(item) ? '#DCF8C5' : 'white',
                // marginLeft: isMyMessage(item) ? 50 : 0,
                // marginRight: isMyMessage(item) ? 0 : 50,
              }
            ]}>
              <View style={{ marginRight: 10 }}>
                { }
                <Image
                  style={styles.tinyLogo}
                  source={{
                    uri: isMyMessage(item) ? 'http://api.mvg-sky.com' + profile?.avatar : 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWbS3I9NbSTEsVOomPr66VVL38-x1RLajLZQ&usqp=CAU',
                  }}
                />
              </View>
              {item.type === 'TEXT' ?
                (
                  <View>
                    {isMyMessage(item) ? <Text style={styles.name}>{user.account.username}</Text> : <Text style={styles.name}>Toan</Text>}
                    {/* <Text style={styles.message}>{item.content}</Text> */}
                    <HTMLView value={item.content} stylesheet={styles.message} />
                    <View style={{ flexDirection: 'column', justifyContent: 'flex-end', width: 300 }}>
                      <Text style={styles.time}>{moment(item.createdAt).fromNow()}</Text>
                    </View>


                  </View>
                ) :
                (
                  item.type === 'URL' ?
                    (
                      <View>
                        {isMyMessage(item) ? <Text style={styles.name}>{user.account.username}</Text> : <Text style={styles.name}>Toan</Text>}
                        <Image
                          style={styles.userImg}
                          source={{ uri: item.content }}
                        />
                        <View style={{ flexDirection: 'column', justifyContent: 'flex-end', width: 300 }}>
                          <Text style={styles.time}>{moment(item.createdAt).fromNow()}</Text>
                        </View>
                      </View>
                    ) :
                    (
                      <View>
                        {isMyMessage(item) ? <Text style={styles.name}>{user.account.username}</Text> : <Text style={styles.name}>Toan</Text>}
                        {/* <HTMLView value={article} stylesheet={styles} /> */}

                        <View style={{ flexDirection: 'column', justifyContent: 'flex-end', width: 300 }}>
                          <Text style={styles.time}>{moment(item.createdAt).fromNow()}</Text>
                        </View>
                      </View>
                    )
                )
              }
            </View>
          </View>
        }
      // inverted

      />
      {/* <InputBox chatRoomID={route.params.id} /> */}
      <RichToolbar
        // style={[styles.richBar]}
        editor={RichText}
        disabled={false}
        selectedIconTint={'#2095F2'}
        disabledIconTint={'#bfbfbf'}
        // onPressAddImage={onPressAddImage}
        actions={[
          actions.keyboard,
          actions.setBold,
          actions.setItalic,
          actions.insertBulletsList,
          actions.insertOrderedList,
          actions.insertLink,
          actions.setStrikethrough,
          actions.setUnderline,
          actions.checkboxList,
          actions.code,
        ]}
      />
      <KeyboardAvoidingView
        behavior={Platform.OS == "ios" ? "padding" : "height"}
        keyboardVerticalOffset={80}
        style={{ width: '100%', height: isShowEmoj ? 400 : 70 }}
      >

        <View style={styles.container2}>
          <View style={styles.mainContainer}>
            <TouchableOpacity onPress={() => {
              setIsShowEmoj(!isShowEmoj)
            }}>
              <FontAwesome5 name="laugh-beam" size={24} color="grey" />
            </TouchableOpacity>
            <RichEditor
              placeholder={"Type a message"}
              disabled={false}
              containerStyle={styles.richEditor}
              ref={RichText}
              style={styles.rich}
              value={message}
              onChange={(text) => setMessage(text)}
              editorInitializedCallback={editorInitializedCallback}
            // multiline
            />

            {/* <TextInput
              placeholder={"Type a message"}
              style={styles.textInput}
              multiline
              numberOfLines={4}
              value={message}
              onChangeText={setMessage}
            ></TextInput> */}
            <TouchableOpacity onPress={() => { handleSendFile() }}><Entypo name="attachment" size={24} color="grey" style={styles.icon} /></TouchableOpacity>
            {!message && <TouchableOpacity onPress={() => { handleCamera() }}><Fontisto name="camera" size={24} color="grey" style={styles.icon} /></TouchableOpacity>}
          </View>
          <TouchableOpacity onPress={onPress}>
            <View style={styles.buttonContainer}>
              {!message
                ? <MaterialCommunityIcons name="microphone" size={28} color="white" />
                : <MaterialIcons name="send" size={28} color="white" />}
            </View>
          </TouchableOpacity>
        </View>


        <EmojiSelector
          onEmojiSelected={emoji => setMessage(message + emoji)}
          showSearchBar={false}
          showTabs={false}
          columns="10"
          category={Categories.emotion}
        />

      </KeyboardAvoidingView>
    </View >

  )
};

export default ChatScreen;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  container1: {
    padding: 10,
  },
  messageBox: {
    borderRadius: 30,
    padding: 10,
    flexDirection: 'row',
  },
  richEditor: {
    flex: 1,
    padding: 0,
    margin: 0,
    marginHorizontal: 10,
    width: 170,
  },
  userImg: {
    height: 150,
    width: 150,
  },
  name: {
    fontSize: 17,
    color: 'green',
    fontWeight: "bold",
    marginBottom: 5,
  },
  message: {

  },
  time: {
    alignSelf: "flex-end",
    color: 'grey',

  },
  mainContainer: {
    flexDirection: 'row',
    backgroundColor: 'white',
    padding: 10,
    borderRadius: 25,
    marginRight: 10,
    flex: 1,
    alignItems: 'flex-end',
  },
  textInput: {
    flex: 1,
    marginHorizontal: 10
  },
  icon: {
    marginHorizontal: 5,
  },
  buttonContainer: {
    backgroundColor: Colors.light.tint,
    borderRadius: 25,
    width: 50,
    height: 50,
    justifyContent: 'center',
    alignItems: 'center',
  },
  container2: {
    flexDirection: 'row',
    margin: 10,
    alignItems: 'flex-end',
  },
  tinyLogo: {
    width: 50,
    height: 50,
    borderRadius: 25,
  },
});

