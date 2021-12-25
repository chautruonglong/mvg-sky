import React, { useContext } from 'react';
import { View, Text, Button, StyleSheet, FlatList } from 'react-native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome'
import { faCoffee, faAngleDoubleRight } from '@fortawesome/free-solid-svg-icons'
import { AuthContext } from '../navigation/AuthProvider';
import ActionButton from 'react-native-action-button';
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5';
import {
  Container,
  Card,
  UserInfo,
  UserImgWrapper,
  UserImg,
  UserInfoText,
  UserName,
  PostTime,
  MailText,
  TextSection,
} from '../styles/MessageStyles';
import HTMLView from "react-native-htmlview";
import moment from "moment";

const trimNewLines = (text) => {
  if (!text) return;
  return text.replace(/(\r\n|\n|\r)/gm, '');
}

const MessagesScreen = ({ navigation }) => {
  const { sendmail, contact } = useContext(AuthContext);

  const getavatar = (item) => {
    let currentimage = ''
    contact.forEach(element => {
      // console.log(element)
      if (element.username === item) {
        currentimage = element.avatar ? `http://api.mvg-sky.com${element.avatar}` : imagedefaul
      }
    })
    // console.log(currentimage)
    return currentimage || `https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWbS3I9NbSTEsVOomPr66VVL38-x1RLajLZQ&usqp=CAU`
  }
  return (
    <Container>
      <FlatList
        data={sendmail}
        keyExtractor={item => item.mailId}
        renderItem={({ item }) => {

          return (
            <Card
              onPress={() => navigation.navigate('Display Mail', {
                from: item.from,
                to: item.to,
                mailTime: item.sendDate,
                emailSubject:
                  item.subject,
                mailBody: item.body,
                userImg: item.userImg,
                attachments: item.attachments,
              }
              )}>
              <UserInfo>
                <UserImgWrapper>
                  <UserImg source={{ uri: `http://api.mvg-sky.com/api/accounts-resources/avatar-by-email/${item.from}` }} />
                </UserImgWrapper>
                <TextSection>
                  <UserInfoText>
                    <UserName numberOfLines={1}>
                      To:  {item.to}</UserName>
                    <PostTime>{moment(item.sendDate).fromNow()}</PostTime>
                  </UserInfoText>
                  <Text style={{ color: '#f0f8ff' }}>{item.subject}</Text>

                  <MailText
                    numberOfLines={1}
                  >
                    {/* <Text>{item.body}</Text> */}
                    <HTMLView value={`${trimNewLines(item.body?.trim())}`} textComponentProps={{ style: { color: '#a9a9a9' } }} stylesheet={styles.message} /></MailText>

                </TextSection>
              </UserInfo>
            </Card>
          )
        }}

      />
      <ActionButton
        buttonColor="#2e64e5"
        title="Add Mail"
        onPress={() => navigation.navigate('New Mail')}>
      </ActionButton>
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
  mainConatinerStyle: {
    flexDirection: 'column',
    flex: 1
  }, floatingMenuButtonStyle: {
    alignSelf: 'flex-end',
    position: 'absolute',
    bottom: 35
  },
  message: {
    color: "#fff"
  }
});
