import React, { useState, useEffect, useContext } from 'react';
import { View, ScrollView, Text, Image, StyleSheet } from 'react-native';
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
import HTMLView from "react-native-htmlview";
import moment from "moment";

const DisplayMailScreen = ({ mail }) => {
  const { user } = useContext(AuthContext)
  const [messages, setMessages] = useState([]);
  const to = `${user.account.username}`

  const namefile = (a) => {
    return decodeURI(a[0].split('/')[a[0].split('/').length - 1])
  }
  return (
    <Container>
      <ScrollView >
        <UserInfo>
          <UserImgWrapper>
            <UserImg source={{ uri: `http://api.mvg-sky.com/api/accounts-resources/avatar-by-email/${mail.from.replace("@", "%40")}` }} />
          </UserImgWrapper>
          <TextSection>
            <UserInfoText>
              <NameSendMai>
                {mail.from}</NameSendMai>
              <PostTime>{moment(mail.mailTime).fromNow()}</PostTime>
            </UserInfoText>
            <MailText numberOfLines={1}>To:  <Text style={styles.to}>{mail.to}</Text>
            </MailText>
          </TextSection>
        </UserInfo>
        <Text style={styles.subject}>{mail.emailSubject}</Text>
        <HTMLView value={mail.mailBody} textComponentProps={{ style: { color: '#fff' } }} stylesheet={styles.message} />
        {mail?.attachments ?
          <View style={{ marginTop: 50 }}>
            <Text style={{ color: "#fff" }}>Attachments:</Text>
            <Image source={require('../assets/file_100px.png')} />
            <HTMLView value={`<a href=\http://api.mvg-sky.com${mail.attachments}\>${namefile(mail.attachments)}`}></HTMLView>
          </View> :
          <></>
        }
      </ScrollView>

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
