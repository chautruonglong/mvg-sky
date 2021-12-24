import React, { useEffect, useContext, useState } from 'react';
import {
    View,
    Text,
    TouchableOpacity,
    ImageBackground,
    TextInput,
    StyleSheet,
    FlatList,
    CheckBox,
    SafeAreaView,
    StatusBar,
    ScrollView,
    Image
} from 'react-native';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import {
    Container,
    UserInfo,
    UserImgWrapper,
    UserImg,
    UserInfoText,
    UserNameContact,
    PostTime,
    MessageTextContact,
    ContacSection,
} from '../styles/MessageStyles';
import ImagePicker from 'react-native-image-crop-picker';
import { Card } from 'react-native-paper';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import Feather from 'react-native-vector-icons/Feather';
import Ionicons from 'react-native-vector-icons/Ionicons';
import FormButton from '../components/FormButton';
import { AuthContext } from '../navigation/AuthProvider';
import Animated from 'react-native-reanimated';
import BottomSheet from 'reanimated-bottom-sheet';
import apiRequest from '../utils/apiRequest';
import Toast from 'react-native-toast-message';
const bodyFormData = new FormData();
const AddMailScreen = ({ navigation }) => {

    const { user, setChats, stompClient, setMyRooms } = useContext(AuthContext);
    const [contact, setContact] = useState(null)
    const [name, setName] = useState(null)
    const [description, setDescription] = useState(null)
    const [image, setImage] = useState("")

    const handleChange = (id) => {
        let temp = contact.map((mycontact) => {
            if (id === mycontact.id) {
                return { ...mycontact, check: !mycontact.check };
            }
            return mycontact;
        });
        setContact(temp);
    };


    useEffect(() => {
        fetchContact()
    }, []);


    const fetchContact = async () => {
        if (user.domain.id) {
            const values = await apiRequest.get(`/contacts?domainIds=${user.domain.id}`)
            const data = values.map(value => {
                value.check = false
                return value
            })
            setContact(data)
        }
    }
    const handleCreateRoom = async () => {
        const customContact = [];
        contact.filter((item) => {
            // console.log({ item })
            if (item?.check) {
                customContact.push(item.accountId)
            }
        })
        customContact.push(user.account.id)
        try {
            const response = await apiRequest.post('/rooms', {
                name: name,
                description: description,
                type: "GROUP",
                accountIds: customContact
            },
                {
                    headers: {
                        accept: 'application/json',
                        // Authorization: `${user.accessToken}`
                    }
                }
            )
            try {
                const response1 = await apiRequest.patch(`/rooms/avatar/${response.id}`,
                    bodyFormData,
                    {
                        headers: { "Content-type": "multipart/form-data" }
                    }
                )
                stompClient.subscribe(
                    `/room/${response.id}`,
                    (payload) => {
                        const chatMessage = {
                            accountId: JSON.parse(payload.body).data.accountId,
                            content: JSON.parse(payload.body).data.content,
                            threadId: null,
                            type: "TEXT",
                            delay: 0
                        }
                        setChats(chatMessage)
                    }
                );
                const rooms = await apiRequest.get(`/rooms?accountId=${user.account.id}`)
                setMyRooms(rooms)
                Toast.show({
                    type: 'success',
                    text1: 'Create room successfully!'
                });
            } catch (error) {
                console.log(error)
                Toast.show({
                    type: 'error',
                    text1: 'Avatar upload failed.'
                });
            }
        }
        catch (error) {
            console.log(error)
            Toast.show({
                type: 'error',
                text1: 'Room create failed.'
            });
        }
    }
    const avtDefault = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWbS3I9NbSTEsVOomPr66VVL38-x1RLajLZQ&usqp=CAU'
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
            // handleUploadimage()
            const imageUri = Platform.OS === 'ios' ? image.sourceURL : image.path;
            setImage(imageUri);
        });
    };
    return (

        <SafeAreaView style={styles.container}>
            <Animated.View
                style={{
                    margin: 20,
                }}>

                {/* <FormButton buttonTitle="Create"
                // onPress={handleUpdate} 
                /> */}

                <View style={styles.action}>
                    <FontAwesome name="bookmark" color="#fff" size={20} />
                    <TextInput
                        placeholder="Name room"
                        placeholderTextColor="#888888"
                        autoCorrect={false}
                        value={
                            name
                        }
                        onChangeText={(txt) => setName(txt)}
                        style={styles.textInput}
                    />
                </View>

                <View style={styles.action}>
                    <Ionicons name="ios-clipboard-outline" color="#fff" size={20} />
                    <TextInput
                        multiline
                        numberOfLines={3}
                        placeholder="Description"
                        placeholderTextColor="#888888"
                        value={
                            description
                        }
                        onChangeText={(txt) => setDescription(txt)}
                        autoCorrect={true}
                        style={[styles.textInput, { height: 40 }]}
                    />

                </View>
                <Text style={styles.text}>Member:</Text>
                <SafeAreaView style={styles.listcontact}>
                    <FlatList
                        data={contact}
                        renderItem={({ item }) => (
                            item.accountId !== user.account.id ?
                                < Card
                                    style={{
                                        borderRadius: 30,
                                        margin: 5,
                                        backgroundColor: "#AAF7B4"
                                    }}>
                                    <View style={{ flexDirection: 'row', }}>
                                        <CheckBox
                                            tintColors={{
                                                true: 'green', false: 'while'
                                            }}
                                            value={item.check}

                                            onValueChange={() => {
                                                handleChange(item.id);
                                            }}

                                            style={styles.checkbox}
                                        />
                                        <UserInfo>
                                            <UserImgWrapper>
                                                <UserImg source={{
                                                    uri: item?.avatar ? 'http://api.mvg-sky.com' + item?.avatar : avtDefault
                                                }} />
                                                {/* <UserImg source={{ uri: item.userImg }} /> */}
                                            </UserImgWrapper>
                                            <ContacSection>
                                                <UserInfoText>
                                                    <UserNameContact>{`${item.firstName} ${item.lastName}`}</UserNameContact>
                                                </UserInfoText>
                                                <MessageTextContact
                                                    numberOfLines={1}
                                                >{item.title}</MessageTextContact>
                                            </ContacSection>


                                        </UserInfo>

                                    </View>
                                </Card>
                                : <></>
                        )
                        }
                        keyExtractor={item => item.id}
                    />
                </SafeAreaView>
                <Text style={styles.text}>Image room:</Text>
                <View style={{ flexDirection: 'row', justifyContent: 'space-around' }}
                >
                    <TouchableOpacity style={{
                    }}
                        onPress={() => {
                            choosePhotoFromLibrary()
                        }}
                    >
                        <ImageBackground
                            style={styles.userImg}
                            source={{ uri: image ? image : avtDefault }}
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
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.checkbox} onPress={() => {
                        handleCreateRoom();
                        navigation.navigate('Messages')
                    }}>
                        <Text style={styles.textbuton}>Create Room</Text>
                    </TouchableOpacity>
                </View>
            </Animated.View>
        </SafeAreaView >
    );
};

export default AddMailScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#000000',
    },
    listcontact: {
        height: 300,
        borderWidth: 1,
        borderColor: "white",
        // marginTop: StatusBar.currentHeight || 0,
    },
    checkbox: {
        color: '#fff',
        alignSelf: "center",
    },
    textbuton: {
        fontSize: 15,
        padding: 10,
        backgroundColor: "#AAF7B4",
        color: '#252e26',
        alignSelf: "center",
        borderRadius: 10,
    },
    item: {
        padding: 20,
        marginVertical: 8,
        marginHorizontal: 16,
    },
    title: {
        fontSize: 32,
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
        height: 200
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
        color: '#333333',
        paddingRight: 30,
        lineHeight: 23,
        flex: 1,
        textAlignVertical: 'top',
        marginLeft: 20,
    },
    text: {
        color: "#fff",
        margin: 10
    }
});
