import React from 'react';
import { View, TouchableOpacity, Text } from 'react-native';
import { createStackNavigator } from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Ionicons from 'react-native-vector-icons/Ionicons';
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5';
import HomeScreen from '../screens/HomeScreen';
import ChatScreen from '../screens/ChatScreen';
import ProfileScreen from '../screens/ProfileScreen';
import AddCallScreen from '../screens/AddCallScreen';
import AddMailScreen from '../screens/AddMailScreen';
import MessagesScreen from '../screens/MessagesScreen';
import MailScreen from '../screens/MailScreen';
import EditProfileScreen from '../screens/EditProfileScreen';
import DisplayMailScreen from '../screens/DisplayMailScreen';
import CreateRoomScreen from '../screens/CreateRoomScreen'
import SendMailScreen from '../screens/SendMailScreen'
const Stack = createStackNavigator();
const Tab = createBottomTabNavigator();

const HomeStack = ({ navigation }) => (
  <Stack.Navigator>
    <Stack.Screen
      name="MVG SKY"
      component={HomeScreen}
      options={{
        headerTitleAlign: 'center',
        headerTitleStyle: {
          color: '#2e64e5',
          fontFamily: 'Kufam-SemiBoldItalic',
          fontSize: 18,
        },
        headerStyle: {
          shadowColor: '#fff',
          elevation: 0,
        },
      }}
    />
  </Stack.Navigator>
);

const MessageStack = ({ navigation }) => (
  <Stack.Navigator>
    <Stack.Screen name="Messages" component={MessagesScreen}
      options={{
        headerTitleAlign: 'center',
        headerTitleStyle: {
          color: '#2e64e5',
          fontFamily: 'Kufam-SemiBoldItalic',
          fontSize: 18,
        },
        headerStyle: {
          shadowColor: '#fff',
          elevation: 0,
        },
      }} />
    <Stack.Screen
      name="Chat"
      component={({ route }) =>
        <ChatScreen title={route.params} />
      }
      options={({ route }) => ({
        headerTitleAlign: 'center',
        headerTitleStyle: {
          color: '#2e64e5',
          fontFamily: 'Kufam-SemiBoldItalic',
          fontSize: 18,
        },
        headerStyle: {
          shadowColor: '#fff',
          elevation: 0,
        },
        title: route.params.userName,
        headerBackTitleVisible: false,
      })}
    />
    <Stack.Screen
      name="CreateRoomScreen"
      component={CreateRoomScreen}
      // options={{ header: () => null }}
      options={({ route }) => ({
        headerBackTitleVisible: false,
      })}
    />
  </Stack.Navigator>
);

const MailStack = ({ navigation }) => (
  <Stack.Navigator>
    <Stack.Screen name="Inbox" component={MailScreen}
      options={{
        headerTitleAlign: 'center',
        headerTitleStyle: {
          color: '#2e64e5',
          fontFamily: 'Kufam-SemiBoldItalic',
          fontSize: 18,
        },
        headerStyle: {
          shadowColor: '#fff',
          elevation: 0,
        },
        headerRight: () => (
          <View style={{ marginRight: 10 }}>
            <FontAwesome5.Button
              name="paper-plane"
              size={22}
              backgroundColor="#fff"
              color="#2e64e5"
              onPress={() => navigation.navigate('Sent')}
            />
          </View>
        ),
      }}
    />
    <Stack.Screen
      name="Display Mail"
      component={({ route }) =>
        <DisplayMailScreen mail={route.params} />
      }
      options={({ route }) => ({
        headerTitleAlign: 'center',
        headerTitleStyle: {
          color: '#2e64e5',
          fontFamily: 'Kufam-SemiBoldItalic',
          fontSize: 18,
        },
        headerStyle: {
          shadowColor: '#fff',
          elevation: 0,
        },
        headerBackTitleVisible: false,
      })
      }
    />
    <Stack.Screen
      name="Sent"
      component={SendMailScreen}
      options={({ route }) => ({
        headerTitleAlign: 'center',
        headerTitleStyle: {
          color: '#2e64e5',
          fontFamily: 'Kufam-SemiBoldItalic',
          fontSize: 18,
        },
        headerStyle: {
          shadowColor: '#fff',
          elevation: 0,
        },
        headerBackTitleVisible: false,
        headerLeft: null,
        headerRight: () => (
          <View style={{ marginRight: 10 }}>
            <FontAwesome5.Button
              name="inbox"
              size={22}
              backgroundColor="#fff"
              color="#2e64e5"
              onPress={() => navigation.navigate('Inbox')}
            />
          </View>
        ),
      })
      }
    />

    <Stack.Screen
      name="New Mail"
      component={AddMailScreen}
      // options={{ header: () => null }}
      options={({ route }) => ({
        headerBackTitleVisible: false,
      })
      }
    />
  </Stack.Navigator>
);

const ProfileStack = ({ navigation }) => (
  <Stack.Navigator>
    <Stack.Screen
      name="Profile"
      component={ProfileScreen}
      options={{
        headerShown: false,
      }}
    />
    <Stack.Screen
      name="EditProfile"
      component={EditProfileScreen}
      options={{
        headerTitleAlign: 'center',
        headerTitleStyle: {
          color: '#2e64e5',
          fontFamily: 'Kufam-SemiBoldItalic',
          fontSize: 18,
        },
        headerStyle: {
          shadowColor: '#fff',
          elevation: 0,
        },
        headerTitle: 'Edit Profile',
        headerBackTitleVisible: false,
        headerStyle: {
          backgroundColor: '#fff',
          shadowColor: '#fff',
          elevation: 0,
        },
      }}
    />
  </Stack.Navigator>
);

const AppStack = () => {

  return (
    <Tab.Navigator
      tabBarOptions={{
        activeTintColor: '#2e64e5',
      }}>
      <Tab.Screen
        name="Call"
        component={HomeStack}
        options={({ route }) => ({
          tabBarLabel: 'Home',
          tabBarIcon: ({ color, size }) => (
            <Ionicons
              name="home"
              color={color}
              size={size}
            />
          ),
        })}
      />

      <Tab.Screen
        name="Messages"
        component={MessageStack}
        options={({ route }) => ({
          tabBarIcon: ({ color, size }) => (
            <Ionicons
              name="chatbox-ellipses-outline"
              color={color}
              size={size}
            />
          ),
        })}
      />
      <Tab.Screen
        name="Mail"
        component={MailStack}
        options={{
          tabBarIcon: ({ color, size }) => (
            <Ionicons name="mail-outline" color={color} size={size} />
          ),
        }}
      />
      <Tab.Screen
        name="Profile"
        component={ProfileStack}
        options={{
          tabBarIcon: ({ color, size }) => (
            <Ionicons name="person-outline" color={color} size={size} />
          ),
        }}
      />
    </Tab.Navigator>
  );
};

export default AppStack;
