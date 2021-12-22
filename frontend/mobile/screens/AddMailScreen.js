import React, { useEffect, useContext, useState } from 'react';
import {
    View,
    Text,
    TouchableOpacity,
    ImageBackground,
    TextInput,
    StyleSheet,
    Alert,
} from 'react-native';

import FontAwesome from 'react-native-vector-icons/FontAwesome';
import Feather from 'react-native-vector-icons/Feather';
import Ionicons from 'react-native-vector-icons/Ionicons';
import FormButton from '../components/FormButton';

import Animated from 'react-native-reanimated';
import BottomSheet from 'reanimated-bottom-sheet';

const AddMailScreen = () => {
    return (
        <View style={styles.container}>
            <View style={styles.container}>
                <BottomSheet
                    snapPoints={[330, -5]}
                    initialSnap={1}
                />
                <Animated.View
                    style={{
                        margin: 20,
                    }}>
                    <View style={{ alignItems: 'center' }}>

                        <Text style={{ marginTop: 10, fontSize: 18, fontWeight: 'bold' }}>
                        </Text>
                        {/* <Text>{user.uid}</Text> */}
                    </View>

                    <View style={styles.action}>
                        <FontAwesome name="user-o" color="#333333" size={20} />
                        <TextInput
                            placeholder="To"
                            placeholderTextColor="#666666"
                            autoCorrect={false}
                            // value={userData ? userData.fname : ''}
                            // onChangeText={(txt) => setUserData({ ...userData, fname: txt })}
                            style={styles.textInput}
                        />
                    </View>
                    <View style={styles.action}>
                        <FontAwesome name="user-o" color="#333333" size={20} />
                        <TextInput
                            placeholder="Cc"
                            placeholderTextColor="#666666"
                            // value={userData ? userData.lname : ''}
                            // onChangeText={(txt) => setUserData({ ...userData, lname: txt })}
                            autoCorrect={false}
                            style={styles.textInput}
                        />
                    </View>
                    <View style={styles.action}>
                        <FontAwesome name="user-o" color="#333333" size={20} />
                        <TextInput
                            placeholder="Bcc"
                            placeholderTextColor="#666666"
                            // value={userData ? userData.lname : ''}
                            // onChangeText={(txt) => setUserData({ ...userData, lname: txt })}
                            autoCorrect={false}
                            style={styles.textInput}
                        />
                    </View>
                    <View style={styles.action}>
                        <Ionicons name="ios-clipboard-outline" color="#333333" size={20} />
                        <TextInput
                            multiline
                            numberOfLines={3}
                            placeholder="Subject"
                            placeholderTextColor="#666666"
                            // value={userData ? userData.about : ''}
                            // onChangeText={(txt) => setUserData({ ...userData, about: txt })}
                            autoCorrect={true}
                            style={[styles.textInput, { height: 40 }]}
                        />
                    </View>
                    <View style={styles.action1}>
                        {/* <Feather name="phone" color="#333333" size={20} /> */}
                        <TextInput
                            placeholder="Body"
                            placeholderTextColor="#666666"

                            autoCorrect={false}
                            // value={userData ? userData.phone : ''}
                            // onChangeText={(txt) => setUserData({ ...userData, phone: txt })}

                            multiline={true}
                            numberOfLines={5}
                            style={styles.textInput1}
                        />
                        <TouchableOpacity style={{ position: 'absolute', bottom: 10, left: 0 }}><Text style={{ color: 'white' }}>DK</Text></TouchableOpacity>
                    </View>
                    <FormButton buttonTitle="Send"
                    // onPress={handleUpdate} 
                    />
                </Animated.View>
            </View>
        </View>
    );
};

export default AddMailScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#000000',
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
        backgroundColor: '#000000',
        paddingTop: 20,
        width: '100%',
    },
    header: {
        backgroundColor: '#000000',
        shadowColor: '#333333',
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
    action1: {
        flexDirection: 'row',
        marginTop: 10,
        marginBottom: 10,
        borderBottomWidth: 1,
        borderBottomColor: '#f2f2f2',
        paddingBottom: 5,
        height: 200,
        paddingBottom: 30
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
        color: '#fff',
    },
    textInput1: {
        color: '#fff',
        paddingRight: 30,
        lineHeight: 23,
        flex: 1,
        textAlignVertical: 'top',
        marginLeft: 20,
    },
});
