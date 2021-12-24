import React, { useState, useEffect, useContext } from 'react';
import { View, ScrollView, Text, Button, StyleSheet } from 'react-native';
import {
  Container,
  Card,
  UserInfo,
  UserImgWrapper,
  UserImg,
  UserInfoText,
  NameSendMai,
  PostTime,
  MailText,
  TextSection,
} from '../styles/MessageStyles';
import { AuthContext } from '../navigation/AuthProvider';

const item = {
  id: '1',
  userName: 'Jenny Doe',
  to: "quoc@acorn.com",
  userImg: require('../assets/users/mail_1.png'),
  emailSubject: 'Send mail',
  mailTime: 'Mar 30',
  mailBody:
    'Hey there, this is my test for a post of my social app in React Native.',
}
const DisplayMailScreen = ({ mail }) => {
  const { user } = useContext(AuthContext)
  const [messages, setMessages] = useState([]);
  const to = `${user.account.username}@${user.domain.name}`
  return (
    <Container>
      <View >
        <UserInfo>
          <UserImgWrapper>
            <UserImg source={mail.userImg} />
          </UserImgWrapper>
          <TextSection>
            <UserInfoText>
              <NameSendMai>
                {mail.userName}</NameSendMai>
              <PostTime>{mail.mailTime}</PostTime>
            </UserInfoText>
            <MailText numberOfLines={1}>To:  <Text style={styles.to}>{to}</Text>
            </MailText>
          </TextSection>
        </UserInfo>
        <Text style={styles.subject}>{mail.emailSubject}</Text>
        <Text style={styles.body}>{mail.mailBody}</Text>

      </View>
    </Container>
  );
};

export default DisplayMailScreen;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#1A1D21"
  },
  header: {
    flexDirection: 'row',
    width: '100%',
    borderBottomWidth: 1,
    borderTopWidth: 1
  },
  to: {
    fontSize: 14,
    color: "#666",
  },
  subject: {
    // marginLeft: -320,
    fontSize: 23,
    fontWeight: "bold",
    marginTop: 20,
    marginBottom: 30,
    color: "#fff"
  },
  body: {
    color: "#fff"
  }
});
