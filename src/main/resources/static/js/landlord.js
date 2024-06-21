document.addEventListener('DOMContentLoaded', function() {
    // Display the first tab content by default
    document.getElementById("tenants").style.display = "block";

    // Other data initialization code can be added here
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

// tenant.js

function confirmLogout() {
    if (confirm("Are you sure you want to logout?")) {
        // Redirect to logout URL
        window.location.href = "/logout";
    }
}

function deleteAccount() {
    if (confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
        // Call the delete account URL
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
function redirectToBuilding(buildingId) {
  window.location.href = "/building/landlords-building-view/" + buildingId;

}



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

    // Fetch landlord ID and role
    const landlordId = document.querySelector('#support').getAttribute('data-landlord-id');
    const role = 'LANDLORD';

    // Prepare issue data
    const issueData = {
        description: description,
        userId: landlordId,
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
    const landlordId = document.querySelector('#support').getAttribute('data-landlord-id');
    const role = 'LANDLORD';

    fetch(`/fetch/issues/${landlordId}/${role}`)
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

