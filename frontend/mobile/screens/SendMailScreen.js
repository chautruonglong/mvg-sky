import React from 'react';
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

// const validIcon = parseIconFromClassName('angle-double-right')

const Mail = [
  {
    id: '1',
    userName: 'Jenny Doe',
    userImg: require('../assets/users/mail_1.png'),
    emailSubject: '[Jira]',
    mailTime: 'Mar 30',
    mailBody:
      'Hey there, this is my test for a post of my social app in React Native.',
  },
  {
    id: '2',
    userName: 'John Doe',
    userImg: require('../assets/users/mail_2.png'),
    emailSubject: '[Register]',
    mailTime: 'Mar 30',
    mailBody:
      'Hey there, this is my test for a post of my social app in React Native.',
  },
  {
    id: '3',
    userName: 'Ken William',
    userImg: require('../assets/users/mail_3.png'),
    emailSubject: '[Login]',
    mailTime: 'Mar 30',
    mailBody:
      'Hey there, this is my test for a post of my social app in React Native.',
  },
  {
    id: '4',
    userName: 'Selina Paul',
    userImg: require('../assets/users/mail_4.png'),
    emailSubject: '[Chat]',
    mailTime: 'Mar 30',
    mailBody:
      'Hey there, this is my test for a post of my social app in React Native.asdadadasdadasdasdasdadadasdadadasdadadadasdadasdadas',
  }
];

const MessagesScreen = ({ navigation }) => {

  return (
    <Container>
      <FlatList
        data={Mail}
        keyExtractor={item => item.id}
        renderItem={({ item }) => (
          <Card
            onPress={() => navigation.navigate('Display Mail', { userName: item.userName, mailTime: item.mailTime, emailSubject: item.emailSubject, mailBody: item.mailBody })}
          >
            <UserInfo>
              <UserImgWrapper>
                <UserImg source={item.userImg} />
              </UserImgWrapper>
              <TextSection>
                <UserInfoText>
                  <UserName>
                    <FontAwesomeIcon style={{ color: '#D2691E' }} icon={faAngleDoubleRight} />
                    {item.userName}</UserName>
                  <PostTime>{item.mailTime}</PostTime>
                </UserInfoText>
                <Text style={{ color: '#f0f8ff' }}>{item.emailSubject}</Text>

                <MailText
                  numberOfLines={1}
                >{item.mailBody}</MailText>

              </TextSection>
            </UserInfo>
          </Card>
        )}

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
  }
});
