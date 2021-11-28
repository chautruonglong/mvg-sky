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

const LoginScreen = ({ navigation }) => {
  const [email, setEmail] = useState();
  const [isvalue, setIsvalue] = useState();
  const [password, setPassword] = useState();
  const { login } = useContext(AuthContext);

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
        // onChangeText={(userEmail) => setEmail(userEmail)}
        onChangeText={(userEmail) => validate(userEmail)}
        isvalue={isvalue}
        placeholderText="Email"
        iconType="user"
        keyboardType="email-address"
        autoCapitalize="none"
        autoCorrect={false}
      />

      <FormInput
        labelValue={password}
        onChangeText={(userPassword) => setPassword(userPassword)}
        placeholderText="Password"
        iconType="lock"
        secureTextEntry={true}
      />
      <Text style={styles.button}></Text>
      <FormButton
        buttonTitle="Sign In"
        // onPress={() => login(email, password)}
        onPress={() => [isvalue ? login(email, password) : null]}
      />

      {/* <TouchableOpacity style={styles.forgotButton} onPress={() => { }}>
        <Text style={styles.navButtonText}>Forgot Password?</Text>
      </TouchableOpacity> */}

      {/* <View style={styles.navButtonTextmain}>
        <Text style={styles.navButtonText1}>
          Don't have an acount?
        </Text>
        <TouchableOpacity
          onPress={() => navigation.navigate('Signup')}>
          <Text style={styles.navButtonText}>
            Create here
          </Text>
        </TouchableOpacity>
      </View> */}
    </ScrollView>
  );
};

export default LoginScreen;

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#FFFFFF',
    // justifyContent: 'center',
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
