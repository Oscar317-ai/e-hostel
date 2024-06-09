// building.js

function toggleSidebar() {
    const sidebar = document.querySelector('.sidebar');
    const mainContainer = document.querySelector('.content-container');
    if (sidebar.classList.contains('collapsed')) {
        sidebar.classList.remove('collapsed');
        mainContainer.style.marginLeft = '150px'; // Adjust the margin to match the sidebar width
    } else {
        sidebar.classList.add('collapsed');
        mainContainer.style.marginLeft = '0';
    }
}

document.addEventListener('DOMContentLoaded', (event) => {
    // Initially show the dashboard section
    showSection('dashboard');
});

function showSection(sectionId) {
    // Hide all content sections
    let sections = document.querySelectorAll('.content-section');
    sections.forEach(section => {
        section.classList.remove('active');
    });

    // Show the selected section
    document.getElementById(sectionId).classList.add('active');
}

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

function redirectToDetails(houseId) {
  window.location.href = "/building/tenants-house-view/" + houseId;
}



