import React, { useRef, useContext, useState } from 'react';
import {
    View,
    Text,
    TouchableOpacity,
    ImageBackground,
    TextInput,
    StyleSheet,
    Alert,
    ScrollView
} from 'react-native';
import Entypo from 'react-native-vector-icons/Entypo';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import Feather from 'react-native-vector-icons/Feather';
import Ionicons from 'react-native-vector-icons/Ionicons';
import FormButton from '../components/FormButton';
import DocumentPicker from 'react-native-document-picker'
import Animated from 'react-native-reanimated';
import BottomSheet from 'reanimated-bottom-sheet';
import apiRequest from '../utils/apiRequest';
import { AuthContext } from '../navigation/AuthProvider';
import Toast from 'react-native-toast-message';
import {
    actions,
    RichEditor,
    RichToolbar,
} from "react-native-pell-rich-editor";

const AddMailScreen = ({ navigation }) => {
    const [to, setTo] = useState("")
    const [bcc, setBcc] = useState("")
    const [cc, setCc] = useState("")
    const [subject, setSubject] = useState("")
    const [body, setBody] = useState("")
    const [namefile, setNamefile] = useState("")
    const [res, setRes] = useState()
    const { user, setSendmail, sendmail } = useContext(AuthContext);
    const RichText = useRef();
    const datamail = new FormData();

    function editorInitializedCallback() {
        RichText.current?.registerToolbar(function (items) {
            // items contain all the actions that are currently active
            // console.log(
            //   items
            // );
        });
    }

    const handleAttachment = async () => {

        try {
            const res = await DocumentPicker.pickSingle({
                type: [DocumentPicker.types.allFiles],
            })

            if (res)
                setRes(res)

            setNamefile(res.name)


        } catch (e) {
            console.log(e)
        }
    }

    const Sendmail = async () => {
        const send = await apiRequest.get(`/mails?accountId=${user.account.id}&mailbox=SENT`, {
            accountId: user?.domain?.id,
            mailbox: "SENT",
        },
            {
                headers: {
                    accept: 'application/json',
                    // Authorization: `${user.accessToken}`
                }
            }
        )
        setSendmail(send)
        navigation.navigate('Sent');
        Toast.show({
            type: 'success',
            text1: 'Sent mail successfully!'
        });
        setNamefile("")
        setTo("")
        setBcc("")
        setCc("")
        setSubject("")
        setBody("")
        setRes("")
    }
    const handleSend = async () => {
        try {
            datamail.append('accountId', user.account.id)
            datamail.append('to', to)
            if (cc)
                datamail.append('cc', cc)
            if (bcc)
                datamail.append('bcc', bcc)
            if (subject)
                datamail.append('subject', subject)
            if (body)
                datamail.append('body', body)
            if (res)
                datamail.append('attachments', {
                    name: res.name,
                    type: res.type,
                    uri: Platform.OS === 'ios' ?
                        res.uri.replace('file://', '')
                        : res.uri,
                })
            datamail.append('enableThread ', true)
            const response = await apiRequest.post(`/mails/send`,
                datamail,
                {
                    headers: { "Content-type": "multipart/form-data" }
                }
            )
            Toast.show({
                type: 'info',
                text1: "Sending mail",
            });
            setTimeout(
                Sendmail
                , 2000);


        } catch (e) {
            console.log(e)
        }
    }
    return (
        <ScrollView style={styles.container}>
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
                    </View>

                    <View style={styles.action}>
                        <FontAwesome name="user-o" color="#333333" size={20} />
                        <TextInput
                            placeholder="To"
                            placeholderTextColor="#666666"
                            autoCorrect={false}
                            value={to}
                            onChangeText={(txt) => setTo(txt)}
                            style={styles.textInput}
                        />
                    </View>
                    <View style={styles.action}>
                        <FontAwesome name="user-o" color="#333333" size={20} />
                        <TextInput
                            placeholder="Cc"
                            placeholderTextColor="#666666"
                            value={cc}
                            onChangeText={(txt) => setCc(txt)}
                            autoCorrect={false}
                            style={styles.textInput}
                        />
                    </View>
                    <View style={styles.action}>
                        <FontAwesome name="user-o" color="#333333" size={20} />
                        <TextInput
                            placeholder="Bcc"
                            placeholderTextColor="#666666"
                            value={bcc}
                            onChangeText={(txt) => setBcc(txt)}
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
                            value={subject}
                            onChangeText={(txt) => setSubject(txt)}
                            autoCorrect={true}
                            style={[styles.textInput, { height: 40 }]}
                        />
                    </View>
                    <View >
                        {/* < RichToolbar
                            // style={[styles.richBar]}
                            editor={RichText}
                            disabled={false}
                            selectedIconTint={'#2095F2'}
                            disabledIconTint={'#bfbfbf'}
                            // onPressAddImage={onPressAddImage}
                            actions={
                                [
                                    actions.keyboard,
                                    actions.setBold,
                                    actions.setItalic,
                                    actions.insertBulletsList,
                                    actions.insertOrderedList,
                                    actions.insertLink,
                                    actions.setStrikethrough,
                                    actions.setUnderline,
                                    actions.checkboxList,
                                    actions.code,
                                ]}
                        /> */}
                    </View>
                    <View style={styles.action1}>
                        {/* <Feather name="phone" color="#333333" size={20} /> */}
                        <TextInput
                            placeholder="Body"
                            placeholderTextColor="#666666"

                            autoCorrect={false}
                            value={body}
                            onChangeText={(txt) => setBody(txt)}

                            multiline={true}
                            numberOfLines={5}
                            style={styles.textInput1}
                        />
                        <View style={{ position: 'absolute', bottom: 10, left: 0, flexDirection: "row" }} >

                            <TouchableOpacity
                                onPress={() => { handleAttachment() }}
                            >
                                <Entypo name="attachment" size={24} color="grey" style={styles.icon} />
                            </TouchableOpacity>
                            {namefile ?
                                <Text style={styles.namefile}>{namefile}</Text>
                                :
                                <></>
                            }
                        </View>


                    </View>

                    {/* <RichEditor
                        placeholder={"Type a message"}
                        disabled={false}
                        containerStyle={styles.richEditor}
                        ref={RichText}
                        style={styles.rich}
                        // value={message}
                        // onChange={(text) => setMessage(text)}
                        editorInitializedCallback={editorInitializedCallback}
                    // multiline
                    /> */}
                    <FormButton buttonTitle="Send"
                        onPress={handleSend}
                    />
                </Animated.View>
            </View>
        </ScrollView>
    );
};

export default AddMailScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#000000',
    },
    namefile: {
        marginLeft: 20,
        color: "#fff"
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
    icon: {
        marginHorizontal: 5,
    },
});
