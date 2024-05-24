'use strict';

const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageFormNews = document.querySelector('#messageFormNews');
const messageInput = document.querySelector('#message');
const messageInputNews = document.querySelector('#messageNews');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector('#chat-messages');
const logout = document.querySelector('#logout');

let stompClient = null;
let nickname = null;
let fullname = null;
let selectedUserId = null;
let jwt = null;

function connect(event) {
    nickname = document.querySelector('#nickname').value.trim();
    jwt = 'Bearer ' + document.querySelector('#jwt').value.trim();

    fullname = nickname;
    selectedUserId = document.querySelector('#anotherperson').value.trim();

    if (nickname && fullname && jwt) {
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({Authorization: jwt}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    stompClient.subscribe(`/user/${nickname}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/user/public`, onMessageReceived);

    // register the connected user
//    stompClient.send("/app/user.addUser",
//        {},
//        JSON.stringify({nickName: nickname, fullName: fullname, status: 'ONLINE'})
//    );
//    document.querySelector('#connected-user-fullname').textContent = fullname;

    usernamePage.classList.add('hidden');
    chatPage.classList.remove('hidden');
    messageForm.classList.remove('hidden');

    fetchAndDisplayUserChat().then();
//    findAndDisplayConnectedUsers().then();
}

//async function findAndDisplayConnectedUsers() {
//    const connectedUsersResponse = await fetch('/users');
//    let connectedUsers = await connectedUsersResponse.json();
//    connectedUsers = connectedUsers.filter(user => user.nickName !== nickname);
//    const connectedUsersList = document.getElementById('connectedUsers');
//    connectedUsersList.innerHTML = '';
//
//    connectedUsers.forEach(user => {
//        appendUserElement(user, connectedUsersList);
//        if (connectedUsers.indexOf(user) < connectedUsers.length - 1) {
//            const separator = document.createElement('li');
//            separator.classList.add('separator');
//            connectedUsersList.appendChild(separator);
//        }
//    });
//}

//function appendUserElement(user, connectedUsersList) {
//    const listItem = document.createElement('li');
//    listItem.classList.add('user-item');
//    listItem.id = user.nickName;
//
//    const userImage = document.createElement('img');
//    userImage.src = '../img/user_icon.png';
//    userImage.alt = user.fullName;
//
//    const usernameSpan = document.createElement('span');
//    usernameSpan.textContent = user.fullName;
//
//    const receivedMsgs = document.createElement('span');
//    receivedMsgs.textContent = '0';
//    receivedMsgs.classList.add('nbr-msg', 'hidden');
//
//    listItem.appendChild(userImage);
//    listItem.appendChild(usernameSpan);
//    listItem.appendChild(receivedMsgs);
//
//    listItem.addEventListener('click', userItemClick);
//
//    connectedUsersList.appendChild(listItem);
//}

//function userItemClick(event) {
//    document.querySelectorAll('.user-item').forEach(item => {
//        item.classList.remove('active');
//    });
//
//    const clickedUser = event.currentTarget;
//    clickedUser.classList.add('active');
//
//    selectedUserId = clickedUser.getAttribute('id');
//    fetchAndDisplayUserChat().then();
//
//    const nbrMsg = clickedUser.querySelector('.nbr-msg');
//    nbrMsg.classList.add('hidden');
//    nbrMsg.textContent = '0';
//
//}

function displayMessage(senderId, content) {
    const messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    if (senderId === nickname) {
        messageContainer.classList.add('sender');
    } else {
        messageContainer.classList.add('receiver');
    }
    const message = document.createElement('p');
    message.textContent = content;
    messageContainer.appendChild(message);
    chatArea.appendChild(messageContainer);
}

async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`api/v1/chat/messages/${nickname}/${selectedUserId}`);
    const userChat = await userChatResponse.json();
    chatArea.innerHTML = '';
    userChat.forEach(chat => {
        displayMessage(chat.senderId, chat.content);
    });
    chatArea.scrollTop = chatArea.scrollHeight;
}


function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            senderId: nickname,
            recipientId: selectedUserId,
            content: messageInput.value.trim(),
        };
        stompClient.send("/app/chat/text", {}, JSON.stringify(chatMessage));
        displayMessage(nickname, messageInput.value.trim());
        messageInput.value = '';
    }
    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}

function sendMessageNews(event) {
    const messageContent = messageInputNews.value.trim();
    if (messageContent && stompClient) {
        const chatMessageNews = {
            senderId: Number(nickname),
            receiverId: Number(selectedUserId),
            newsId: Number(messageInputNews.value.trim()),
        };
        stompClient.send("/app/chat/news", {}, JSON.stringify(chatMessageNews));
        messageInputNews.value = '';
    }
    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}


async function onMessageReceived(payload) {
//    await findAndDisplayConnectedUsers();
    console.log('Message received', payload);
    const message = JSON.parse(payload.body);
    console.log('selectedUserId', selectedUserId)
    console.log('message.senderId', message.senderId)
    console.log('selectedUserId === message.senderId', selectedUserId === message.senderId)
    if (selectedUserId && selectedUserId === message.senderId) {
        displayMessage(message.senderId, message.content);
        chatArea.scrollTop = chatArea.scrollHeight;
    } else {
        displayMessage(nickname, message.content);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

//    if (selectedUserId) {
//        document.querySelector(`#${selectedUserId}`).classList.add('active');
//    } else {
//        messageForm.classList.add('hidden');
//    }

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
}

function onLogout() {
//    stompClient.send("/app/user.disconnectUser",
//        {},
//        JSON.stringify({nickName: nickname, fullName: fullname, status: 'OFFLINE'})
//    );
    window.location.reload();
}

usernameForm.addEventListener('submit', connect, true); // step 1
messageForm.addEventListener('submit', sendMessage, true);
messageFormNews.addEventListener('submit', sendMessageNews, true);
window.onbeforeunload = () => onLogout();

//Current jwt (admin)
//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5jb20iLCJpYXQiOjE3MTY1NTI2MTQsImV4cCI6MTcxNjU4ODYxNH0.Yum9RpOhq1iqAH3YQ3dmFZkU9Zk2c61SIir9UXCtsVQ