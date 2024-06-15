document.addEventListener("DOMContentLoaded", function() {
    const editButton = document.getElementById("edit-house-button");
    const backButton = document.getElementById("back-button");
    const deleteButton = document.getElementById("delete-house-button");
    const editForm = document.getElementById("editHouseForm");
    const updateForm = document.getElementById("update-house-form");

    editButton.addEventListener("click", function() {
        editForm.style.display = "block";
        window.scrollTo(0, editForm.offsetTop);
    });

    backButton.addEventListener("click", function() {
        history.back();
    });

    deleteButton.addEventListener("click", function() {
        const confirmDelete = confirm("Are you sure you want to delete this house?");
        if (confirmDelete) {
            const houseId = updateForm.querySelector('input[name="houseId"]').value;
            fetch(`/house/delete/${houseId}`, { method: 'GET' })
                .then(response => {
                    if (response.ok) {
                        alert("House deleted successfully.");
                        history.back();
                    } else {
                        alert("Failed to delete the house.");
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert("An error occurred while deleting the house.");
                });
        }
    });
});
