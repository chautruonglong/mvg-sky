'use strict'

// import AbstractXHRObject from 'sockjs-client/lib/transport/browser/abstract-xhr';

const _start = AbstractXHRObject.prototype._start;

AbstractXHRObject.prototype._start = function(method, url, payload, opts) {
    if (!opts) {
        opts = { noCredentials : true };
    }
    return _start.call(this, method, url, payload, opts);
};

let stompClient
let username

const connect = (event) => {
    username = document.querySelector('#username').value.trim()

    if (username) {
        const login = document.querySelector('#login')
        login.classList.add('hide')

        const chatPage = document.querySelector('#chat-page')
        chatPage.classList.remove('hide')


        const socket = new SockJS('http://api.mvg-sky.com/api/chats/ws')
        stompClient = Stomp.over(socket)
        stompClient.connect({}, onConnected, onError)
    }
    event.preventDefault()
}

const onConnected = () => {
    stompClient.subscribe('/room/ebe438af-d924-4a12-a4b6-c8e839b8921b', onMessageReceived)
    const status = document.querySelector('#status')
    status.className = 'hide'
}

const onError = (error) => {
    const status = document.querySelector('#status')
    status.innerHTML = 'Could not find the connection you were looking for. Move along. Or, Refresh the page!'
    status.style.color = 'red'
}

const sendMessage = (event) => {
    const messageInput = document.querySelector('#message')
    const [messageContent, delay] = messageInput.value.trim().split("$")

    if (messageContent && stompClient) {
        const chatMessage = {
            accountId: '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d',
            content: messageContent,
            threadId: null,
            type: "TEXT",
            delay
        }
        stompClient.send("/chat/send-message/ebe438af-d924-4a12-a4b6-c8e839b8921b", {}, JSON.stringify(chatMessage))
        messageInput.value = ''
    }
    event.preventDefault();
}


const onMessageReceived = (payload) => {
    const message = JSON.parse(payload.body).data;

    const chatCard = document.createElement('div')
    chatCard.className = 'card-body'

    const flexBox = document.createElement('div')
    flexBox.className = 'd-flex justify-content-end mb-4'
    chatCard.appendChild(flexBox)

    const messageElement = document.createElement('div')
    messageElement.className = 'msg_container_send'

    flexBox.appendChild(messageElement)

    messageElement.classList.add('chat-message')

    const avatarContainer = document.createElement('div')
    avatarContainer.className = 'img_cont_msg'
    const avatarElement = document.createElement('div')
    avatarElement.className = 'circle user_img_msg'
    const avatarText = document.createTextNode(message.accountId[0])
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message.accountId)
    avatarContainer.appendChild(avatarElement)

    messageElement.style['background-color'] = getAvatarColor(message.accountId)

    flexBox.appendChild(avatarContainer)

    const time = document.createElement('span')
    time.className = 'msg_time_send'
    time.innerHTML = message.createdAt
    messageElement.appendChild(time)

    messageElement.innerHTML = message.content

    const chat = document.querySelector('#chat')
    chat.appendChild(flexBox)
    chat.scrollTop = chat.scrollHeight
}

const hashCode = (str) => {
    let hash = 0
    for (let i = 0; i < str.length; i++) {
       hash = str.charCodeAt(i) + ((hash << 5) - hash)
    }
    return hash
}


const getAvatarColor = (messageSender) => {
    const colours = ['#2196F3', '#32c787', '#1BC6B4', '#A1B4C4']
    const index = Math.abs(hashCode(messageSender) % colours.length)
    return colours[index]
}

const loginForm = document.querySelector('#login-form')
loginForm.addEventListener('submit', connect, true)
const messageControls = document.querySelector('#message-controls')
messageControls.addEventListener('submit', sendMessage, true)
