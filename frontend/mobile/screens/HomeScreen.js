import React, { useEffect, useState } from 'react';
import {
  View,
  ScrollView,
  Text,
  StyleSheet,
  FlatList,
  SafeAreaView,
  Alert,
  ImageBackground
} from 'react-native';

const image = require('../assets/backgroud.jpg');
const HomeScreen = ({ navigation }) => {

  return (
    <SafeAreaView style={styles.container}>
      <ImageBackground source={{ uri: 'https://media.giphy.com/media/BHWoX8f4EqKcPTALTK/giphy.gif' }} resizeMode="cover" style={styles.image}>
      </ImageBackground>
    </SafeAreaView>
  );
};

export default HomeScreen;
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#1A1D21',
  },
  image: {
    flex: 1,
    justifyContent: "center"
  },
})