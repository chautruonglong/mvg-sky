import React, { useContext, useState, useEffect } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  Image,
  CheckBox,
  StyleSheet,
  ScrollView
} from 'react-native';
import Toast from 'react-native-toast-message';
import FormInput from '../components/FormInput';
import FormButton from '../components/FormButton';
import { AuthContext } from '../navigation/AuthProvider';
import apiRequest from "../utils/apiRequest"
import AsyncStorage from '@react-native-community/async-storage'


const STORAGE_KEYNAME = '@save_username'
const STORAGE_KEYPASS = '@save_password'
const STORAGE_KEYTEXTBOX = '@save_textbox'
const STORAGE_KEYTOKEN = '@save_token'
const LoginScreen = ({ navigation }) => {
  const [email, setEmail] = useState();
  const [isvalue, setIsvalue] = useState();
  const [isvaluepass, setIsvaluepass] = useState();
  const [password, setPassword] = useState();
  const { setUser } = useContext(AuthContext);
  const [isSelected, setSelection] = useState(false);
  validate = (text) => {
    let reg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w\w+)+$/;
    if (reg.test(text) === false) {
      setIsvalue(false);
    }
    else {
      setEmail(text)
      setIsvalue(true)
    }
  }
  validatepass = (text) => {
    let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/;
    if (reg.test(text) === false) {
      setIsvaluepass(false);
    }
    else {
      setPassword(text)
      setIsvaluepass(true)
    }
  }

  const saveData = async () => {
    try {
      await AsyncStorage.setItem(STORAGE_KEYNAME, email)
      await AsyncStorage.setItem(STORAGE_KEYPASS, password)
      let textboxsave = "0"
      if (isSelected)
        textboxsave = "1"
      await AsyncStorage.setItem(STORAGE_KEYTEXTBOX, textboxsave)
    } catch (e) {
      alert('Failed to save the data to the storage')
    }
  }

  const readData = async () => {
    try {
      const myemail = await AsyncStorage.getItem(STORAGE_KEYNAME)
      const mypass = await AsyncStorage.getItem(STORAGE_KEYPASS)
      const textbox = await AsyncStorage.getItem(STORAGE_KEYTEXTBOX)
      if (myemail !== null && mypass !== null) {
        setEmail(myemail)
        setPassword(mypass)
        if (textbox == "1")
          setSelection(true)
        else
          setSelection(false)
      }
      const token = await AsyncStorage.getItem(STORAGE_KEYTOKEN)
    } catch (e) {
      console.log(e)
      alert('Failed to fetch the data from storage')
    }
  }

  useEffect(() => {
    readData()
  }, [])

  const clearStorage = async () => {
    try {
      await AsyncStorage.clear()
      console.log('Storage successfully cleared!')
    } catch (e) {
      console.log('Failed to clear the async storage.')
    }
  }

  const handleLogin = async (email, password) => {
    try {
      if (isSelected)
        saveData()
      else
        clearStorage()
      const response = await apiRequest.post('/accounts/login', {
        email: email,
        password: password,
      },
        {
          headers: {
            accept: 'application/json',
            // Authorization: `${user.accessToken}`
          }
        }
      )
      await AsyncStorage.setItem(STORAGE_KEYTOKEN, response.accessToken)
      setUser(response)
      Toast.show({
        type: 'success',
        text1: 'Login successfully!'
      });

    } catch (error) {
      console.log(error)
      setUser(null)
      Toast.show({
        type: 'error',
        text1: 'Wrong username or password.'
      });
    }
  }

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Image
        source={require('../assets/logo.png')}
        style={styles.logo}
      />
      <Text style={styles.text}>MVG SKY</Text>
      <Text style={styles.button}></Text>
      <FormInput
        labelValue={email}
        onChangeText={(userEmail) => { setEmail(userEmail); validate(userEmail) }}
        isvalue={isvalue}
        placeholderText="Email"
        iconType="user"
        keyboardType="email-address"
        autoCapitalize="none"
        autoCorrect={false}
      />

      <FormInput
        labelValue={password}
        onChangeText={(userPassword) => { setPassword(userPassword); validatepass(userPassword) }}
        isvalue={isvaluepass}
        placeholderText="Password"
        iconType="lock"
        secureTextEntry={true}
      />
      <View style={styles.checkboxContainer}>
        <CheckBox
          value={isSelected}
          onValueChange={setSelection}
          style={styles.checkbox}
        />
        <Text style={styles.label}>Save for next time</Text>
      </View>
      <FormButton
        buttonTitle="Sign In"
        onPress={() => handleLogin(email, password)}
      />
    </ScrollView>
  );
};

export default LoginScreen;

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#FFFFFF',
    alignItems: 'center',
    padding: 20,
    paddingTop: 50,
    height: '100%',
  },
  checkboxContainer: {
    flexDirection: "row",
    marginBottom: 20,
  },
  logo: {
    height: 100,
    width: 100,
    resizeMode: 'cover',
  },
  label: {
    margin: 7,
  },
  text: {
    fontFamily: 'TimesNewRomanPSMT',
    fontSize: 28,
    marginBottom: 60,
    color: '#051d5f',
  },
  navButton: {
    marginTop: 15,
  },
  button: {
    marginTop: 20,
  },
  forgotButton: {
    marginVertical: 35,
  },
  navButtonText: {
    marginLeft: 2,
    fontSize: 18,
    color: '#2e64e5',
    fontFamily: 'RobotoCondensed-Regular',
  },
  navButtonText1: {
    fontSize: 18,
    color: '#778CAC',
    fontStyle: 'italic',
    fontFamily: 'RobotoCondensed-Italic',
  },
  navButtonTextmain: {
    flexDirection: 'row',
    marginVertical: 35,
  }
});
