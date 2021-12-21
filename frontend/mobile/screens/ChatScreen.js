import React, { memo, useRef, useState, useContext, useEffect, useCallback } from 'react';
import { AuthContext } from '../navigation/AuthProvider';
import Colors from "./Colors";
import BottomSheet from 'reanimated-bottom-sheet';
import Toast from 'react-native-toast-message';
import { FlatList, TextInput, Image, ListItem, View, Vibration, KeyboardAvoidingView, Text, Button, StyleSheet, TouchableOpacity, ScrollView } from 'react-native';
import { GiftedChat, Send, InputToolbar, Actions, Composer, MessageText, Avatar } from 'react-native-gifted-chat';
import Animated from 'react-native-reanimated';
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
import Popover from 'react-native-popover-view';

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
  const bodyFormData = new FormData();

  const { stompClient } = useContext(AuthContext);
  const { user, profile, chats, setMyRooms } = useContext(AuthContext)
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
  const [isShowDropdown, setIsShowDropdown] = useState(false)
  const [currentUser, setCurrentUser] = useState({})
  const [isShowReply, setIsShowReply] = useState(false)

  useEffect(() => {
    fetchMessage()
  }, []);

  const isImage = (fileName) => {
    return !!fileName?.match(/\.(jpg|jpeg|png|gif)$/gi);
  };

  const fetchMessage = async () => {
    if (title.roomId) {
      const values = await apiRequest.get(`/messages?roomId=${title.roomId}&limit=15`)
      setMessages(values.reverse())
    }
  }

  const fetchRoom = async () => {
    const rooms = await apiRequest.get(`/rooms?accountId=${user.account.id}`)
    setMyRooms(rooms)
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
        setMessages([...messages, chats])
      }
      else {
        Toast.show({
          type: 'info',
          // text1: title.userName,
          text1: messages.content,
        });
        setMessages([...messages, chats])
      }
      fetchRoom()
    }
  }, [chats]);

  const onSend = () => {
    const chatMessage = {
      accountId: user?.account?.id,
      content: currentUser?.content ? `<ReplyMessage>${currentUser.content}</ReplyMessage>${message}` : message,
      threadId: null,
      type: "TEXT",
      delay: 0
    }
    setIsShowReply(false)
    setCurrentUser({})
    setMessage("")
    RichText.current.setContentHTML("")
    stompClient.send(`/chat/send-message/${title.roomId}`, JSON.stringify(chatMessage), {},)

  };

  const onSendReply = (seconds) => {
    const chatMessage = {
      accountId: user?.account?.id,
      content: currentUser?.content ? `<ReplyMessage>${currentUser.content}</ReplyMessage>${message}` : message,
      threadId: null,
      type: "TEXT",
      delay: seconds
    }
    setIsShowReply(false)
    setCurrentUser({})
    setMessage("")
    RichText.current.setContentHTML("")
    stompClient.send(`/chat/send-message/${title.roomId}`, JSON.stringify(chatMessage), {},)

  };

  const handleCamera = async () => (
    ImagePicker.openPicker({
      width: 300,
      height: 300,
      cropping: true,
      compressImageQuality: 0.7,
    }).then(async (image) => {
      const imageUri = Platform.OS === 'ios' ? image.sourceURL : image.path;
      bodyFormData.append('accountId', user.account.id)
      bodyFormData.append('content ', {
        name: image.path.split('/').pop(),
        type: image.mime,
        uri: Platform.OS === 'android' ? image.path : image.path.replace('file://', ''),
      })
      bodyFormData.append('type', 'MEDIA')
      bodyFormData.append('delay', 0)
      try {
        const response = await apiRequest.post(`/messages/send-media/${title.roomId}`,
          bodyFormData,
          {
            headers: { "Content-type": "multipart/form-data" }
          }
        )
      }
      catch (error) {
        console.log(error)
      }
      // const chatMessage = {
      //   accountId: user?.account?.id,
      //   content: imageUri,
      //   threadId: null,
      //   type: "URL",
      //   delay: 0
      // }
      // stompClient.send(`/chat/send-message/${title.roomId}`, JSON.stringify(chatMessage), {},)
      // setMessages([...messages, chatMessage])
      // console.log(imageUri);
    })

  );
  const handleSendFile = async () => {
    const datafile = new FormData();
    try {
      const res = await DocumentPicker.pickSingle({
        type: [DocumentPicker.types.allFiles],
      })
      datafile.append('accountId', user.account.id)
      datafile.append('content', {
        name: res.name,
        type: res.type,
        uri: Platform.OS === 'ios' ?
          res.uri.replace('file://', '')
          : res.uri,
      })
      datafile.append('type', 'MEDIA')
      datafile.append('delay', 0)
      const response = await apiRequest.post(`/messages/send-media/${title.roomId}`,
        datafile,
        {
          headers: { "Content-type": "multipart/form-data" }
        }
      )

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
  const ReplyMessage = () => {
    setIsShowReply(true);
    bs.current.snapTo(1);
  }
  const SendMessageAfter = (second) => {
    console.log(second);
    onSendReply(second)
    bss.current.snapTo(1);
  }
  renderInner = () => (
    <View style={styles.panel}>
      <TouchableOpacity
        style={styles.panelButton}
        onPress={ReplyMessage}
      >
        <Text style={styles.panelButtonTitle}>Reply</Text>
      </TouchableOpacity>
    </View>
  );

  renderInner = () => (
    <View
      style={styles.panel}
    >
      <TouchableOpacity
        style={styles.panelButton}
        onPress={ReplyMessage}
      >
        <Text style={styles.panelButtonTitle}>Reply</Text>
      </TouchableOpacity>
    </View>
  );

  renderInnerSend = () => (
    <View style={styles.panel}>
      <View style={{ alignItems: 'center' }}>
        <Text style={styles.panelSubtitle}>Send Message After</Text>
        <></>
      </View>
      <TouchableOpacity
        style={styles.panelButton}
        onPress={() => {
          SendMessageAfter(5)
        }}
      >
        <Text style={styles.panelButtonTitle}>5s</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.panelButton}
        onPress={() => {
          SendMessageAfter(10)
        }}
      >
        <Text style={styles.panelButtonTitle}>10s</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.panelButton}
        onPress={() => {
          SendMessageAfter(30)
        }}
      >
        <Text style={styles.panelButtonTitle}>30s</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.panelButton}
        onPress={() => bs.current.snapTo(1)}>
        <Text style={styles.panelButtonTitle}>Cancel</Text>
      </TouchableOpacity>
    </View>
  );

  renderHeader = () => (
    <View style={styles.header}>
      <View style={styles.panelHeader}>
        <View style={styles.panelHandle} />
      </View>
    </View>
  );

  bs = React.createRef();
  fall = new Animated.Value(1);
  bss = React.createRef();
  fallsend = new Animated.Value(1);
  return (
    <View style={styles.container}>
      <BottomSheet
        ref={bs}
        snapPoints={[330, -5]}
        renderContent={renderInner}
        renderHeader={renderHeader}
        initialSnap={1}
        callbackNode={fall}
        enabledGestureInteraction={true}
      />
      <Animated.View
        style={{
          opacity: Animated.add(0.1, Animated.multiply(fall, 1.0)),
        }}></Animated.View>
      <BottomSheet
        ref={bss}
        snapPoints={[330, -5]}
        renderContent={renderInnerSend}
        renderHeader={renderHeader}
        initialSnap={1}
        callbackNode={fallsend}
        enabledGestureInteraction={true}
      />
      <Animated.View
        style={{
          opacity: Animated.add(0.1, Animated.multiply(fall, 1.0)),
        }}></Animated.View>
      <FlatList
        ref={yourRef}
        onContentSizeChange={() => yourRef.current.scrollToEnd()}
        onLayout={() => yourRef.current.scrollToEnd()}
        style={{ width: '100%' }}
        data={messages}
        renderItem={({ item }) => {
          const currentReplyMessage = item?.content.split('<ReplyMessage>').pop().split('</ReplyMessage>')[0];
          const customMessage = item?.content.split('</ReplyMessage>')[1];


          return (
            <>
              <TouchableOpacity style={styles.container1} onLongPress={() => {
                bs.current.snapTo(0)
                // setIsShowDropdown(true)
                setCurrentUser({ userName: item?.accountId, content: customMessage || item?.content })
              }}>

                <View style={[
                  styles.messageBox, {
                    backgroundColor: isMyMessage(item) ? '#DCF8C5' : 'white',
                    // marginLeft: isMyMessage(item) ? 50 : 0,
                    // marginRight: isMyMessage(item) ? 0 : 50,
                  }
                ]}>
                  {currentReplyMessage !== item?.content && (
                    <View style={{ marginTop: 6, height: 40, marginLeft: 4, paddingLeft: 10, borderLeftWidth: 2, marginBottom: 20 }}>
                      <Text>Reply:</Text>
                      <HTMLView value={currentReplyMessage} stylesheet={styles.message} />
                    </View>
                  )}

                  <View style={{ flexDirection: 'row', }}>
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
                          <HTMLView value={customMessage || item.content} stylesheet={styles.message} />
                          <View style={{ flexDirection: 'column', justifyContent: 'flex-end', width: 300 }}>
                            <Text style={styles.time}>{moment(item.createdAt).fromNow()}</Text>
                          </View>


                        </View>
                      ) :
                      (
                        item.type === 'MEDIA' ?
                          (
                            <>
                              {isImage(item.content) ?
                                <View>
                                  {isMyMessage(item) ? <Text style={styles.name}>{user.account.username}</Text> : <Text style={styles.name}>Toan</Text>}
                                  <Image
                                    style={styles.userImg}
                                    source={{ uri: 'http://api.mvg-sky.com' + item.content }}
                                  />
                                  <View style={{ flexDirection: 'column', justifyContent: 'flex-end', width: 300 }}>
                                    <Text style={styles.time}>{moment(item.createdAt).fromNow()}</Text>
                                  </View>
                                </View>
                                : (
                                  <View>
                                    {isMyMessage(item) ? <Text style={styles.name}>{user.account.username}</Text> : <Text style={styles.name}>Toan</Text>}
                                    <Image source={require('../assets/file.png')} />
                                    <HTMLView value={`<a href=\http://api.mvg-sky.com${item.content}\>${decodeURI(item.content.split('/')[item.content.split('/').length - 1])}</a>`}></HTMLView>
                                    <View style={{ flexDirection: 'column', justifyContent: 'flex-end', width: 300 }}>
                                      <Text style={styles.time}>{moment(item.createdAt).fromNow()}</Text>
                                    </View>
                                  </View>
                                )}
                            </>
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
              </TouchableOpacity>
            </>
          )
        }

        }
        keyExtractor={item => item.id}
      // inverted

      />
      <Popover
        isVisible={isShowDropdown}
        onRequestClose={() => setIsShowDropdown(false)}
        from={(
          <View />
        )}>
        <TouchableOpacity
          onPress={() => setIsShowReply(true)}>
          {/* {isShowReply ? */}
          <Text>Reply</Text>
          {/* } */}
        </TouchableOpacity>
      </Popover>
      {/* < Popover
        isVisible={isShowDropdown}
        from={(
          <TouchableOpacity>
            <Text>Press here to open popover!</Text>
          </TouchableOpacity>
        )}
        onRequestClose={() => setIsShowDropdown(false)}
      >
        <TouchableOpacity style={{ height: 40, width: 100, borderWidth: 1 }}>
          Reply
        </TouchableOpacity>
      </Popover > */}
      {/* <InputBox chatRoomID={route.params.id} /> */}
      {
        isShowReply && <View style={{ height: 60, width: '100%', paddingLeft: 10, paddingTop: 4, borderTopWidth: 1, borderTopColor: '#CCCCCC' }}>
          <Text style={{ fontSize: 10 }}>Reply to {currentUser?.userName}</Text>
          <HTMLView value={currentUser?.content} stylesheet={styles.message} />
          <TouchableOpacity
            onPress={() => setIsShowReply(false)}
            style={{
              position: 'absolute',
              right: 10,
              top: 10,
              height: 20,
              width: 20,
              flexDirection: 'column',
              justifyContent: 'center',
              alignItems: 'center',
              borderWidth: 1,
              borderRadius: 100
            }}><Text>X</Text></TouchableOpacity>
        </View>
      }
      < RichToolbar
        // style={[styles.richBar]}
        editor={RichText}
        disabled={false}
        selectedIconTint={'#2095F2'}
        disabledIconTint={'#bfbfbf'}
        // onPressAddImage={onPressAddImage}
        actions={
          [
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
            <TouchableOpacity onPress={() => { handleCamera() }}
            ><Fontisto name="camera" size={24} color="grey" style={styles.icon} /></TouchableOpacity>
          </View>
          <TouchableOpacity onPress={onPress} onLongPress={() => {
            bss.current.snapTo(0)
          }}>
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
  commandButton: {
    padding: 15,
    borderRadius: 10,
    backgroundColor: '#FF6347',
    alignItems: 'center',
    marginTop: 10,
  },
  panel: {
    padding: 20,
    backgroundColor: '#FFFFFF',
    paddingTop: 20,
    width: '100%',
    height: 500,
  },
  header: {
    backgroundColor: '#FFFFFF',
    shadowColor: '#FFFFFF',
    shadowOffset: { width: -1, height: -3 },
    shadowRadius: 2,
    shadowOpacity: 0.4,
    paddingTop: 20,
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
  },
  panelHeader: {
    alignItems: 'center',
  },
  panelHandle: {
    width: 40,
    height: 8,
    borderRadius: 4,
    backgroundColor: '#00000040',
    marginBottom: 10,
  },
  panelButton: {
    padding: 13,
    borderRadius: 10,
    backgroundColor: '#2e64e5',
    alignItems: 'center',
    marginVertical: 7,
  },
  panelTitle: {
    fontSize: 27,
    height: 35,
  },
  panelSubtitle: {
    fontSize: 17,
    color: 'gray',
    height: 30,
    marginBottom: 10,
  },
  panelButton: {
    padding: 13,
    borderRadius: 10,
    backgroundColor: '#2e64e5',
    alignItems: 'center',
    marginVertical: 7,
  },
  panelButtonTitle: {
    fontSize: 17,
    fontWeight: 'bold',
    color: 'white',
  },
});

