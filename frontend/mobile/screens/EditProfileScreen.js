import React, { useEffect, useContext, useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  ImageBackground,
  TextInput,
  StyleSheet,
  Alert,
  KeyboardAvoidingView,
} from 'react-native';

import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import Feather from 'react-native-vector-icons/Feather';
import Ionicons from 'react-native-vector-icons/Ionicons';
import FormButton from '../components/FormButton';
import Toast from 'react-native-toast-message';
import Animated from 'react-native-reanimated';
import BottomSheet from 'reanimated-bottom-sheet';
import ImagePicker from 'react-native-image-crop-picker';
import apiRequest from "../utils/apiRequest"
import { AuthContext } from '../navigation/AuthProvider';
// import firestore from '@react-native-firebase/firestore';
// import storage from '@react-native-firebase/storage';

const EditProfileScreen = () => {
  const { profile, setProfile, user } = useContext(AuthContext);
  const [image, setImage] = useState(null);
  const [userData, setUserData] = useState(null);
  var bodyFormData = new FormData();

  const handleUploadimage = async () => {
    try {
      const response = await apiRequest.patch(`/profiles/avatar/${profile.id}`,
        bodyFormData
        ,
        {
          headers: { "Content-type": "multipart/form-data" }
        }
      )
      console.log(bodyFormData)
      setProfile(response)
      Toast.show({
        type: 'success',
        text1: 'Avatar change successfully!'
      });
    } catch (error) {
      console.log(error)
      Toast.show({
        type: 'error',
        text1: 'Avatar change failed.'
      });
    }
  }

  const handleUpdate = async () => {
    try {
      const response = await apiRequest.put(`/profiles/${profile.id}`,
        {
          firstName: profile.firstName,
          lastName: profile.lastName,
          title: profile.title,
          mobile: profile.mobile,
          birthday: profile.birthday,
          location: profile.location
        },
        {
          headers: {
            accept: 'application/json',
            // Authorization: `${user.accessToken}`
          }
        }
      )
      Toast.show({
        type: 'success',
        text1: 'Profile change successfully!'
      });

    } catch (error) {
      console.log(error)
      Toast.show({
        type: 'error',
        text1: 'Profile change failed.'
      });
    }
  }

  const takePhotoFromCamera = () => {
    ImagePicker.openCamera({
      compressImageMaxWidth: 300,
      compressImageMaxHeight: 300,
      cropping: true,
      compressImageQuality: 0.7,
    }).then((image) => {
      bodyFormData.append('avatar', image)
      handleUploadimage()
      const imageUri = Platform.OS === 'ios' ? image.sourceURL : image.path;
      setImage(image);
      bs.current.snapTo(1);
    });
  };

  const choosePhotoFromLibrary = () => {
    ImagePicker.openPicker({
      width: 300,
      height: 300,
      cropping: true,
      compressImageQuality: 0.7,
    }).then((image) => {
      bodyFormData.append('avatar', {
        name: image.path.split('/').pop(),
        type: image.mime,
        uri: Platform.OS === 'android' ? image.path : image.path.replace('file://', ''),
      })
      handleUploadimage()
      const imageUri = Platform.OS === 'ios' ? image.sourceURL : image.path;
      setImage(imageUri);
      bs.current.snapTo(1);
    });
  };

  renderInner = () => (
    <View style={styles.panel}>
      <View style={{ alignItems: 'center' }}>
        <Text style={styles.panelTitle}>Upload Photo</Text>
        <Text style={styles.panelSubtitle}>Choose Your Profile Picture</Text>
      </View>
      <TouchableOpacity
        style={styles.panelButton}
        onPress={takePhotoFromCamera}>
        <Text style={styles.panelButtonTitle}>Take Photo</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.panelButton}
        onPress={choosePhotoFromLibrary}>
        <Text style={styles.panelButtonTitle}>Choose From Library</Text>
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


  const bs = React.createRef();
  const fall = new Animated.Value(1);

  return (
    <KeyboardAvoidingView behavior={Platform.OS === "ios" ? "padding" : "height"} style={styles.container}>
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
            margin: 20,
            opacity: Animated.add(0.1, Animated.multiply(fall, 1.0)),
          }}>
          <View style={{ alignItems: 'center' }}>
            <TouchableOpacity
              onPress={() => bs.current.snapTo(0)}
            >
              <View
                style={{
                  height: 100,
                  width: 100,
                  borderRadius: 15,
                  justifyContent: 'center',
                  alignItems: 'center',
                }}>
                <ImageBackground
                  style={styles.userImg}
                  source={{ uri: 'http://api.mvg-sky.com' + profile?.avatar }}
                  style={{ height: 100, width: 100 }}
                  imageStyle={{ borderRadius: 15 }}>
                  <View
                    style={{
                      flex: 1,
                      justifyContent: 'center',
                      alignItems: 'center',
                    }}>
                    <MaterialCommunityIcons
                      name="camera"
                      size={35}
                      color="#fff"
                      style={{
                        opacity: 0.7,
                        alignItems: 'center',
                        justifyContent: 'center',
                        borderWidth: 1,
                        borderColor: '#fff',
                        borderRadius: 10,
                      }}
                    />
                  </View>
                </ImageBackground>
              </View>
            </TouchableOpacity>
            <Text style={{ marginTop: 10, fontSize: 18, fontWeight: 'bold', color: "#FFFFFF" }}>
              {userData ? userData.fname : ''} {userData ? userData.lname : ''}
            </Text>
          </View>

          <View style={styles.action}>
            <FontAwesome name="user-o" color="#FFFFFF" size={20} />
            <TextInput
              selectTextOnFocus={false}
              placeholder="First Name"
              placeholderTextColor="#FFFFFF"
              autoCorrect={false}
              value={profile ? profile.firstName : ''}
              onChangeText={(txt) => setProfile({ ...profile, firstName: txt })}
              style={styles.textInput}
            />
          </View>
          <View style={styles.action}>
            <FontAwesome name="user-o" color="#FFFFFF" size={20} />
            <TextInput
              selectTextOnFocus={false}
              placeholder="Last Name"
              placeholderTextColor="#FFFFFF"
              onChangeText={(txt) => setProfile({ ...profile, lastName: txt })}
              autoCorrect={false}
              value={profile ? profile.lastName : ''}
              style={styles.textInput}
            />
          </View>
          <View style={styles.action}>
            <Ionicons name="ios-clipboard-outline" color="#FFFFFF" size={20} />
            <TextInput
              selectTextOnFocus={false}
              multiline
              numberOfLines={3}
              placeholder="Title Me"
              placeholderTextColor="#FFFFFF"
              value={profile ? profile.title : ''}
              onChangeText={(txt) => setProfile({ ...profile, title: txt })}
              autoCorrect={true}
              style={[styles.textInput, { height: 40 }]}
            />
          </View>
          <View style={styles.action}>
            <Feather name="phone" color="#FFFFFF" size={20} />
            <TextInput
              selectTextOnFocus={false}
              placeholder="Mobile"
              placeholderTextColor="#FFFFFF"
              keyboardType="number-pad"
              autoCorrect={false}
              value={profile ? profile.mobile : ''}
              onChangeText={(txt) => setProfile({ ...profile, mobile: txt })}
              style={styles.textInput}
            />
          </View>
          <View style={styles.action}>
            <MaterialCommunityIcons
              name="map-marker-outline"
              color="#FFFFFF"
              size={20}
            />
            <TextInput
              selectTextOnFocus={false}
              placeholder="City"
              placeholderTextColor="#FFFFFF"
              autoCorrect={false}
              value={profile ? profile.location : ''}
              onChangeText={(txt) => setProfile({ ...profile, location: txt })}
              style={styles.textInput}
            />
          </View>
          <FormButton buttonTitle="Update"
            onPress={handleUpdate}
          />
        </Animated.View>
      </View>
    </KeyboardAvoidingView>
  );
};

export default EditProfileScreen;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#1A1D21',
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