import React, { useState, useEffect, useContext } from 'react';
import {
  View,
  Text,
  Image,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
  SafeAreaView,
  ImageBackground,
  TextInput,
} from 'react-native';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import { AuthContext } from '../navigation/AuthProvider';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import Feather from 'react-native-vector-icons/Feather';
import Ionicons from 'react-native-vector-icons/Ionicons';
import Toast from 'react-native-toast-message';
import apiRequest from "../utils/apiRequest"

// import firestore from '@react-native-firebase/firestore';

const ProfileScreen = ({ navigation, route }) => {
  const { user, setUser, logout, profile } = useContext(AuthContext);

  const handlLogout = async () => {
    try {
      const response = await apiRequest.delete(`/accounts/${user.account.id}/logout`, {
        data: {
          refreshToken: user.refreshToken
        }
      })
      Toast.show({
        type: 'success',
        text2: 'logout success!'
      });
      setUser(null)
    } catch (error) {
      console.log(error)
      Toast.show({
        type: 'error',
        text1: 'logout failed.'
      });
    }
  }
  return (
    <SafeAreaView style={{ flex: 1, backgroundColor: '#fff' }}>
      <ScrollView
        style={styles.container}
        contentContainerStyle={{ justifyContent: 'center', alignItems: 'center' }}
        showsVerticalScrollIndicator={false}>
        <Image
          style={styles.userImg}
          source={{ uri: 'http://api.mvg-sky.com' + profile?.avatar }}
        // source={{ uri: 'http://api.mvg-sky.com/api/accounts-resources/avatar/a6e7bccb-16b5-4779-a5b7-68693eec3937.jpg' }}

        // profile?.avatar
        />
        <Text style={styles.userName}>{user.account.username}</Text>
        <Text style={styles.titleUser}>
          {profile?.title || 'No details added.'}
        </Text>
        <View style={styles.userBtnWrapper}>
          {route.params ? (
            <>
              <TouchableOpacity style={styles.userBtn} onPress={() => { }}>
                <Text style={styles.userBtnTxt}>Message</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.userBtn} onPress={() => { }}>
                <Text style={styles.userBtnTxt}>Follow</Text>
              </TouchableOpacity>
            </>
          ) : (
            <>
              <TouchableOpacity
                style={styles.userBtn}
                onPress={() => {
                  navigation.navigate('EditProfile');
                }}>
                <Text style={styles.userBtnTxt}>Edit</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.userBtn} onPress={() => handlLogout()}>
                <Text style={styles.userBtnTxt}>Logout</Text>
              </TouchableOpacity>
            </>
          )}
        </View>

        <View style={styles.action}>
          <FontAwesome name="user-o" color="#FFFFFF" size={20} />
          <TextInput
            editable={false}
            selectTextOnFocus={false}
            placeholder="First Name"
            placeholderTextColor="#FFFFFF"
            autoCorrect={false}
            value={profile ? profile.firstName : ''}
            style={styles.textInput}
          />
        </View>
        <View style={styles.action}>
          <FontAwesome name="user-o" color="#FFFFFF" size={20} />
          <TextInput
            editable={false}
            selectTextOnFocus={false}
            placeholder="Last Name"
            placeholderTextColor="#FFFFFF"
            value={profile ? profile.lastName : ''}
            autoCorrect={false}
            style={styles.textInput}
          />
        </View>
        <View style={styles.action}>
          <Ionicons name="ios-clipboard-outline" color="#FFFFFF" size={20} />
          <TextInput
            editable={false}
            selectTextOnFocus={false}
            multiline
            numberOfLines={3}
            placeholder="Title Me"
            placeholderTextColor="#FFFFFF"
            value={profile ? profile.title : ''}
            autoCorrect={true}
            style={[styles.textInput, { height: 40 }]}
          />
        </View>
        <View style={styles.action}>
          <Feather name="phone" color="#FFFFFF" size={20} />
          <TextInput
            editable={false}
            selectTextOnFocus={false}
            placeholder="Mobile"
            placeholderTextColor="#FFFFFF"
            keyboardType="number-pad"
            autoCorrect={false}
            value={profile ? profile.mobile : ''}
            style={styles.textInput}
          />
        </View>
        {/* 
        <View style={styles.action}>
          <FontAwesome name="globe" color="#FFFFFF" size={20} />
          <TextInput
            editable={false}
            selectTextOnFocus={false}
            placeholder="Country"
            placeholderTextColor="#FFFFFF"
            autoCorrect={false}
            value={profile ? profile.country : ''}
            style={styles.textInput}
          />
        </View> */}
        <View style={styles.action}>
          <MaterialCommunityIcons
            name="map-marker-outline"
            color="#FFFFFF"
            size={20}
          />
          <TextInput
            editable={false}
            selectTextOnFocus={false}
            placeholder="City"
            placeholderTextColor="#FFFFFF"
            autoCorrect={false}
            value={profile ? profile.location : ''}
            style={styles.textInput}
          />
        </View>

      </ScrollView>
    </SafeAreaView >
  );
};

export default ProfileScreen;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#1A1D21',
    padding: 20,
  },
  userImg: {
    height: 150,
    width: 150,
    borderRadius: 75,
  },
  userName: {
    fontSize: 18,
    fontWeight: 'bold',
    marginTop: 10,
    marginBottom: 10,
    color: '#FFFFFF',
  },
  titleUser: {
    fontSize: 12,
    fontWeight: '600',
    color: '#FFFFFF',
    textAlign: 'center',
    marginBottom: 10,
  },
  userBtnWrapper: {
    flexDirection: 'row',
    justifyContent: 'center',
    width: '100%',
    marginBottom: 10,
  },
  userBtn: {
    borderColor: '#2e64e5',
    borderWidth: 2,
    borderRadius: 3,
    paddingVertical: 8,
    paddingHorizontal: 12,
    marginHorizontal: 5,
  },
  userBtnTxt: {
    color: '#2e64e5',
  },
  userInfoWrapper: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    width: '100%',
    marginVertical: 20,
  },
  userInfoItem: {
    justifyContent: 'center',
  },
  userInfoTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 5,
    textAlign: 'center',
  },
  userInfoSubTitle: {
    fontSize: 12,
    color: '#666',
    textAlign: 'center',
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
  panelTitle: {
    fontSize: 27,
    height: 35,
  },
  panelSubtitle: {
    fontSize: 14,
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
  action: {
    flexDirection: 'row',
    marginTop: 10,
    marginBottom: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#f2f2f2',
    paddingBottom: 5,
  },
  actionError: {
    flexDirection: 'row',
    marginTop: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#FF0000',
    paddingBottom: 5,
  },
  textInput: {
    flex: 1,
    marginTop: Platform.OS === 'ios' ? 0 : -12,
    paddingLeft: 10,
    color: '#FFFFFF',
  },
});



  // const fetchPosts = async () => {
  //   try {
  //     const list = [];

  //     await firestore()
  //       .collection('posts')
  //       .where('userId', '==', route.params ? route.params.userId : user.uid)
  //       .orderBy('postTime', 'desc')
  //       .get()
  //       .then((querySnapshot) => {
  //         // console.log('Total Posts: ', querySnapshot.size);

  //         querySnapshot.forEach((doc) => {
  //           const {
  //             userId,
  //             post,
  //             postImg,
  //             postTime,
  //             likes,
  //             comments,
  //           } = doc.data();
  //           list.push({
  //             id: doc.id,
  //             userId,
  //             userName: 'Test Name',
  //             userImg:
  //               'https://lh5.googleusercontent.com/-b0PKyNuQv5s/AAAAAAAAAAI/AAAAAAAAAAA/AMZuuclxAM4M1SCBGAO7Rp-QP6zgBEUkOQ/s96-c/photo.jpg',
  //             postTime: postTime,
  //             post,
  //             postImg,
  //             liked: false,
  //             likes,
  //             comments,
  //           });
  //         });
  //       });

  //     setPosts(list);

  //     if (loading) {
  //       setLoading(false);
  //     }

  //     console.log('Posts: ', posts);
  //   } catch (e) {
  //     console.log(e);
  //   }
  // };

  // const getUser = async () => {
  //   await firestore()
  //     .collection('users')
  //     .doc(route.params ? route.params.userId : user.uid)
  //     .get()
  //     .then((documentSnapshot) => {
  //       if (documentSnapshot.exists) {
  //         console.log('User Data', documentSnapshot.data());
  //         setUserData(documentSnapshot.data());
  //       }
  //     })
  // }

  // useEffect(() => {
  //   getUser();
  //   fetchPosts();
  //   navigation.addListener("focus", () => setLoading(!loading));
  // }, [navigation, loading]);

  // const handleDelete = () => { };