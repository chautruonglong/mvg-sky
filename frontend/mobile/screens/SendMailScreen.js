import React, { useContext } from 'react';
import { View, Text, Button, StyleSheet, FlatList } from 'react-native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome'
import { faCoffee, faAngleDoubleRight } from '@fortawesome/free-solid-svg-icons'
import { AuthContext } from '../navigation/AuthProvider';
import ActionButton from 'react-native-action-button';
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

const trimNewLines = (text) => {
  if (!text) return;
  return text.replace(/(\r\n|\n|\r)/gm, '');
}

const MessagesScreen = ({ navigation }) => {
  const { inboxmail, contact } = useContext(AuthContext);

  const getavatar = (item) => {
    let currentimage = ''
    contact.forEach(element => {
      // console.log(element)
      if (element.username === item) {
        currentimage = element.avatar ? `http://api.mvg-sky.com${element.avatar}` : imagedefaul
      }
    })
    // console.log(currentimage)
    return currentimage || `Toan`
  }
  return (
    <Container>
      <FlatList
        data={inboxmail}
        keyExtractor={item => item.id}
        renderItem={({ item }) => {

          return (
            <Card
              onPress={() => navigation.navigate('Display Mail', { userName: item.userName, mailTime: item.mailTime, emailSubject: item.emailSubject, mailBody: item.mailBody, userImg: item.userImg })}
            >
              <UserInfo>
                <UserImgWrapper>
                  <UserImg source={getavatar(item.from)} />
                </UserImgWrapper>
                <TextSection>
                  <UserInfoText>
                    <UserName>
                      <FontAwesomeIcon style={{ color: '#D2691E' }} icon={faAngleDoubleRight} />
                      {item.from}</UserName>
                    <PostTime>{item.mailTime}</PostTime>
                  </UserInfoText>
                  <Text style={{ color: '#f0f8ff' }}>{item.subject}</Text>

                  <MailText
                    numberOfLines={1}
                  >
                    <HTMLView value={`${trimNewLines(item.body?.trim())}`} textComponentProps={{ style: { color: '#fff' } }} stylesheet={styles.message} /></MailText>

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
