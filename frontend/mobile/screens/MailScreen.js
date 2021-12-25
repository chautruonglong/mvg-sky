import React, { useContext } from 'react';
import { View, Text, Button, StyleSheet, FlatList } from 'react-native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome'
import { faCoffee, faAngleDoubleRight } from '@fortawesome/free-solid-svg-icons'
import { AuthContext } from '../navigation/AuthProvider';
import ActionButton from 'react-native-action-button';
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
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
import apiRequest from '../utils/apiRequest';

const trimNewLines = (text) => {
  if (!text) return;
  return text.replace(/(\r\n|\n|\r)/gm, '');
}

const MessagesScreen = ({ navigation }) => {
  const { inboxmail, contact } = useContext(AuthContext);

  const handlesetstatus = async (id) => {
    const values = await apiRequest.patch(`/mails/change-status/${id}`,
      {
        "status": "SEEN"
      }
    )
  }
  return (
    <Container>
      <FlatList
        data={inboxmail}
        keyExtractor={item => item.id}
        renderItem={({ item }) => {

          return (
            <Card
              onPress={() => {
                handlesetstatus(item.mailId)
                navigation.navigate('Display Mail', {
                  from: item.from,
                  to: item.to,
                  mailTime: item.sendDate,
                  emailSubject:
                    item.subject,
                  mailBody: item.body,
                  userImg: item.userImg,
                  attachments: item.attachments,
                })
              }}
            >
              <UserInfo>
                <UserImgWrapper>
                  <UserImg source={{ uri: `http://api.mvg-sky.com/api/accounts-resources/avatar-by-email/${item.from.replace("@", "%40")}` }} />
                </UserImgWrapper>
                <TextSection>
                  <UserInfoText>
                    <UserName numberOfLines={1}>
                      {!item.isSeen ?
                        < FontAwesome
                          name="circle"
                          style={{ color: '#00b5ec', }}
                        />
                        :
                        < FontAwesome
                          name="circle"
                          style={{ color: '#1D2229', }}
                        />}  {item.from}</UserName>
                    <PostTime>{moment(item.sendDate).fromNow()}</PostTime>
                  </UserInfoText>
                  <Text numberOfLines={1} style={{ color: '#f0f8ff' }}>    {item.subject}</Text>

                  <MailText >
                    {/* <HTMLView value={item.body} textComponentProps={{ style: { color: '#a9a9a9' } }} stylesheet={styles.message} /></MailText> */}
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
