import React, { useEffect, useState, useRef, useCallback } from 'react';
import { StyleSheet, Vibration, Text, View, Button } from 'react-native';
import { GiftedChat, Composer } from 'react-native-gifted-chat';
import { SwipeRow } from 'react-native-swipe-list-view';
import { Provider } from 'react-redux';
import store from './redux/store';

import { useReduxState, useMemoSelector, useSetState } from 'use-redux-states';

const conversationId = 1;
const App = () => {
    const chat = useRef();

    const { setState: setChatState, getState: getChatState } = useReduxState({
        name: `conversations.${conversationId}`,
        state: {
            text: '',
            replyId: null,
        },
    });

    const { setState: setMessages, getState: getMessages } = useReduxState({
        name: `messages`,
        state: [
            {
                _id: 2,
                text: 'Hello developer',
                createdAt: new Date(),
                user: {
                    _id: 2,
                    name: 'React Native',
                    avatar: 'https://facebook.github.io/react/img/logo_og.png',
                },
            },
            {
                _id: 1,
                system: true,
                text: 'Swipe to reply',
                createdAt: new Date(),
                user: {
                    _id: 1,
                },
            },
        ],
        cleanup: true,
    });

    const messages = useMemoSelector(`messages`);

    const onInputTextChanged = (text) => {
        setChatState({ text });
    };

    const renderBubble = ({ currentMessage }) => (
        <Bubble
            id={currentMessage._id}
            text={currentMessage.text}
            replyId={currentMessage.replyId}
        />
    );

    const renderComposer = ({ textInputProps }) => (
        <MessageInput
            chat={chat}
            onInputTextChanged={onInputTextChanged}
            textInputProps={textInputProps}
        />
    );

    const onSend = (messages = []) => {
        const replyId = getChatState((state) => state?.replyId);
        if (replyId) {
            messages.map((msg) => (msg.replyId = replyId));
        }
        GiftedChat.append(getMessages(), messages);
        setMessages(messages, (prevM, newM) => [...newM, ...prevM]);
        setChatState({ replyId: null, text: '' });
    };

    return (
        <GiftedChat
            ref={chat}
            renderComposer={renderComposer}
            renderBubble={renderBubble}
            messages={messages}
            onSend={onSend}
            user={{
                _id: 1,
            }}
        />
    );
};

const Bubble = ({ text, replyId, system, selecting, trashed, id }) => {
    const setChatState = useSetState(`conversations.${conversationId}`);

    const onLeftAction = useCallback(
        ({ isActivated }) => {
            if (isActivated) {
                setChatState({ replyId: id });
                Vibration.vibrate(50);
            }
        },
        [id]
    );

    return (
        <SwipeRow
            useNativeDriver
            onLeftActionStatusChange={onLeftAction}
            disableLeftSwipe
            disableRightSwipe={!!(system || trashed || selecting)}
            leftActivationValue={90}
            leftActionValue={0}
            style={styles.swipe}
            swipeKey={id + ''}>
            <></>
            <Message text={text} replyId={replyId} />
        </SwipeRow>
    );
};

const Message = ({ text, replyId }) => {
    return (
        <View>
            {replyId && <Reply id={replyId} />}
            <Button title={text} />
        </View>
    );
};

const MessageInput = ({ onInputTextChanged, textInputProps, chat }) => {
    const { text, replyId } =
        useMemoSelector(`conversations.${conversationId}`) || {};

    return (
        <View style={{ flex: 1 }}>
            {replyId && <ReplyWrapper id={replyId} />}
            <View style={styles.composerWrapper}>
                <Composer
                    text={text}
                    onTextChanged={onInputTextChanged}
                    textInputProps={textInputProps}
                />
                <Button
                    onPress={() => chat.current.onSend({ text }, true)}
                    title={'Send'}
                />
            </View>
        </View>
    );
};

const ReplyWrapper = ({ id }) => {
    const setChatState = useSetState(`conversations.${conversationId}`);
    const resetReply = () => setChatState({ replyId: null });
    return (
        <View style={styles.ReplyWrapper}>
            <Reply id={id} />
            <Button onPress={resetReply} title={'X'} />
        </View>
    );
};

const Reply = ({ id }) => {
    const message =
        useMemoSelector(`messages`, (messages = []) =>
            messages.find(({ _id }) => _id === id)
        ) || {};

    return <Text>{message?.text}</Text>;
};

export default () => {
    return (
        <Provider store={store}>
            <App />
        </Provider>
    );
};

const styles = StyleSheet.create({
    ReplyWrapper: {
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'space-between',
    },
    composerWrapper: {
        flex: 1,
        flexDirection: 'row',
    },
});
