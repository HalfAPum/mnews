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

let stompClient = null;
let nickname = null;
let fullname = null;
let selectedUserId = null;
let jwt = null;

function connect(event) {
    jwt = 'Bearer ' + document.querySelector('#jwt').value.trim();

    fetch(`api/v1/chat/getUserId`,{
        headers: {
             'Authorization': jwt,
        }
    }).then(response => response.json()).then(res => {
        nickname = '' + res;

        console.log("User id from jwt " + nickname);

        fullname = nickname;
        selectedUserId = document.querySelector('#anotherperson').value.trim();

        if (nickname && fullname && jwt) {
            const socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);

            stompClient.connect({Authorization: jwt}, onConnected, onError);
        }
    });

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
}

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
    const userChatResponse = await fetch(`api/v1/chat/messages/${selectedUserId}`,{
         headers: {
              'Authorization': jwt,
         }
     });
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
            recipientId: Number(selectedUserId),
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

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
}

usernameForm.addEventListener('submit', connect, true); // step 1
messageForm.addEventListener('submit', sendMessage, true);
messageFormNews.addEventListener('submit', sendMessageNews, true);
