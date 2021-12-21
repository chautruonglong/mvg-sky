import React, { useContext, useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  Image,
  Platform,
  StyleSheet,
  ScrollView
} from 'react-native';
import Toast from 'react-native-toast-message';
import FormInput from '../components/FormInput';
import FormButton from '../components/FormButton';
import { AuthContext } from '../navigation/AuthProvider';
import apiRequest from "../utils/apiRequest"



const LoginScreen = ({ navigation }) => {
  const [email, setEmail] = useState();
  const [isvalue, setIsvalue] = useState();
  const [isvaluepass, setIsvaluepass] = useState();
  const [password, setPassword] = useState();
  const { setUser } = useContext(AuthContext);

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

  const handleLogin = async (email, password) => {
    try {
      const response = await apiRequest.post('/accounts/login', {
        email: 'quoc@a.com',
        password: 'Khong123@',
        // email: email,
        // password: password,
      },
        {
          headers: {
            accept: 'application/json',
            // Authorization: `${user.accessToken}`
          }
        }
      )
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
        // labelValue={email}
        onChangeText={(userEmail) => validate(userEmail)}
        isvalue={isvalue}
        placeholderText="Email"

        iconType="user"
        keyboardType="email-address"
        autoCapitalize="none"
        autoCorrect={false}
      />

      <FormInput
        // labelValue={password}
        onChangeText={(userPassword) => validatepass(userPassword)}
        isvalue={isvaluepass}

        placeholderText="Password"
        iconType="lock"
        secureTextEntry={true}
      />
      <Text style={styles.button}></Text>
      <FormButton
        buttonTitle="Sign In"
        onPress={() => handleLogin(email, password)}

      // onPress={() => [isvalue & isvaluepass ? handleLogin(email, password) : null]}
      />

      {/* <TouchableOpacity style={styles.forgotButton} onPress={() => { }}>
        <Text style={styles.navButtonText}>Forgot Password?</Text>
      </TouchableOpacity> */}
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
  logo: {
    height: 100,
    width: 100,
    resizeMode: 'cover',
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
