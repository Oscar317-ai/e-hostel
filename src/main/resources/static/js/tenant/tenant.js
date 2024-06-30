

document.addEventListener('DOMContentLoaded', () => {
     const faqs = document.querySelectorAll('.faq-item h4');
     faqs.forEach(faq => {
        faq.addEventListener('click', () => {
             const answer = faq.nextElementSibling;
                 if (answer.style.display === 'block') {
                    answer.style.display = 'none';
                 } else {
                    answer.style.display = 'block';
                }
         });
    });
    // Display the first tab content by default
    document.getElementById("home-listings").style.display = "block";
    // Initialize reported issues
    loadIssues();
    connect();
});


function openSection(evt, sectionName) {
    // Hide all tab contents
    const tabcontents = document.getElementsByClassName("tabcontent");
    for (let i = 0; i < tabcontents.length; i++) {
        tabcontents[i].style.display = "none";
    }

    // Remove the background color of all tablinks
    const tablinks = document.getElementsByClassName("tablink");
    for (let i = 0; i < tablinks.length; i++) {
        tablinks[i].style.backgroundColor = "";
    }

    // Show the current tab content and add an "active" class to the clicked button
    document.getElementById(sectionName).style.display = "block";
    evt.currentTarget.style.backgroundColor = "#e14e50";
}

function confirmLogout() {
    if (confirm("Are you sure you want to logout?")) {
        stompClient.send("/app/user.disconnectUser",
                {},
                JSON.stringify({userId: tenantId, userName: firstname, status: 'ONLINE'})
            );
        // Redirect to logout URL
        window.location.href = "/logout";
    }
}



// Notifications tab
'use strict';

const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector('#chat-messages');
const toggleUsersListButton = document.querySelector('#toggleUsersList');
const usersList = document.querySelector('.users-list');

let stompClient = null;
let tenantId = document.querySelector('#support').getAttribute('data-tenant-id');
const nickname = document.querySelector('#support').getAttribute('data-tenant-id');
const fullname = document.querySelector('#chat-page').getAttribute('data-tenant-firstname');
let selectedUserId = null;

function connect() {

    if (nickname && fullname) {
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
}

function onConnected() {
    stompClient.subscribe(`/user/${nickname}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/user/public`, onMessageReceived);

    // register the connected user
    stompClient.send("/app/user.addUser",
        {},
        JSON.stringify({nickName: nickname, fullName: fullname, status: 'ONLINE'})
    );
    document.querySelector('#connected-user-fullname').textContent = fullname;
    findAndDisplayConnectedUsers().then();
}

async function findAndDisplayConnectedUsers() {
    const connectedUsersResponse = await fetch('/users');
    let connectedUsers = await connectedUsersResponse.json();
    connectedUsers = connectedUsers.filter(user => user.nickName !== nickname);
    const connectedUsersList = document.getElementById('connectedUsers');
    connectedUsersList.innerHTML = '';

    connectedUsers.forEach(user => {
        appendUserElement(user, connectedUsersList);
        if (connectedUsers.indexOf(user) < connectedUsers.length - 1) {
            const separator = document.createElement('li');
            separator.classList.add('separator');
            connectedUsersList.appendChild(separator);
        }
    });
}

function appendUserElement(user, connectedUsersList) {
    const listItem = document.createElement('li');
    listItem.classList.add('user-item');
    listItem.id = user.nickName;

    const userImage = document.createElement('img');
    userImage.src = '/img/user.jpg  ';
    userImage.alt = user.fullName;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.fullName;

    const receivedMsgs = document.createElement('span');
    receivedMsgs.textContent = '0';
    receivedMsgs.classList.add('nbr-msg', 'hidden');

    listItem.appendChild(userImage);
    listItem.appendChild(usernameSpan);
    listItem.appendChild(receivedMsgs);

    listItem.addEventListener('click', userItemClick);

    connectedUsersList.appendChild(listItem);
}

function userItemClick(event) {
    document.querySelectorAll('.user-item').forEach(item => {
        item.classList.remove('active');
    });
    messageForm.classList.remove('hidden');

    const clickedUser = event.currentTarget;
    clickedUser.classList.add('active');

    selectedUserId = clickedUser.getAttribute('id');
    fetchAndDisplayUserChat().then();

    const nbrMsg = clickedUser.querySelector('.nbr-msg');
    nbrMsg.classList.add('hidden');
    nbrMsg.textContent = '0';

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
    const userChatResponse = await fetch(`/messages/${nickname}/${selectedUserId}`);
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
            timestamp: new Date()
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        displayMessage(nickname, messageInput.value.trim());
        messageInput.value = '';
    }
    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}

async function onMessageReceived(payload) {
    await findAndDisplayConnectedUsers();
    console.log('Message received', payload);
    const message = JSON.parse(payload.body);
    if (selectedUserId && selectedUserId === message.senderId) {
        displayMessage(message.senderId, message.content);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    if (selectedUserId) {
        document.querySelector(`#${selectedUserId}`).classList.add('active');
    } else {
        messageForm.classList.add('hidden');
    }

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '1';
    }
}

messageForm.addEventListener('submit', sendMessage, true);
toggleUsersListButton.addEventListener('click', toggleUsersList, true);
window.onbeforeunload = () => onLogout();




// Support tab

document.addEventListener('DOMContentLoaded', () => {
    // Your existing code
    loadIssues();
});

function reportIssue() {
    const description = document.getElementById('issue-description').value;
    if (description.trim() === '') {
        alert('Please describe your issue.');
        return;
    }

    // Fetch tenant ID and role
    const tenantId = document.querySelector('#support').getAttribute('data-tenant-id');
    const role = 'TENANT';

    // Prepare issue data
    const issueData = {
        description: description,
        userId: tenantId,
        role: role
    };

    fetch('/create/issue', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(issueData)
    })
    .then(response => response.json())
    .then(data => {
        alert('Issue reported successfully!');
        loadIssues(); // Reload the issues to include the new one
        document.getElementById('issue-description').value = '';
    })
    .catch(error => {
        console.error('Error:', error);
        alert('There was an error reporting your issue.');
    });
}

function loadIssues() {

    const role = 'TENANT';

    fetch(`/fetch/issues/${tenantId}/${role}`)
        .then(response => response.json())
        .then(issues => {
            const issueList = document.getElementById('issue-list');
            issueList.innerHTML = '';
            issues.forEach(issue => {
                const li = document.createElement('li');

                // Create status label
                const statusLabel = document.createElement('span');
                statusLabel.textContent = issue.status === 'PENDING' ? 'P' : 'S';
                statusLabel.className = issue.status === 'PENDING' ? 'status-label pending' : 'status-label solved';

                // Create description text
                const descriptionText = document.createElement('span');
                descriptionText.textContent = issue.description;

                // Append elements to list item
                li.appendChild(statusLabel);
                li.appendChild(descriptionText);

                // Create and append delete button
                const deleteButton = document.createElement('button');
                deleteButton.textContent = 'Delete';
                deleteButton.className = 'delete-button';
                deleteButton.onclick = () => deleteIssue(issue.id);
                li.appendChild(deleteButton);

                // Append list item to issue list
                issueList.appendChild(li);
            });
        });
}

document.addEventListener('DOMContentLoaded', () => {
    loadIssues();
});

function deleteIssue(id) {
    fetch(`/remove/issue/${id}`, {
        method: 'DELETE'
    })
    .then(() => loadIssues());
}



// contact us
document.getElementById('contact-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const contactData = {
        name: document.getElementById('name').value,
        email: document.getElementById('user-email').value,
        message: document.getElementById('message').value
    };

    fetch('/api/contact', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(contactData)
    })
    .then(response => response.json())
    .then(data => {
        alert('Your message has been sent successfully!');
        document.getElementById('contact-form').reset();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('There was an error sending your message.');
    });
});



// Add more JavaScript functionalities as needed
function deleteAccount() {
    if (confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
        fetch('/delete-account', { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    alert("Account deleted successfully.");
                    window.location.href = "/logout";
                } else {
                    alert("Failed to delete account.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("An error occurred while deleting your account.");
            });
    }
}

function redirectToHouse(houseId) {
    window.location.href = "/house/tenant-house/" + houseId;
}

function redirectToBuilding(buildingId) {
    window.location.href = "/building/tenants-building-view/" + buildingId;
}
