document.addEventListener("DOMContentLoaded", function() {
    const modal = document.getElementById("myModal");
    const loadingAnimation = document.getElementById("loadingAnimation");

    window.openModal = function() {
        modal.style.display = "block";
    }

    window.closeModal = function() {
        modal.style.display = "none";
    }

    window.submitForm = function(event) {
        event.preventDefault();
        if (!document.getElementById("termsCheckbox").checked) {
            alert("Please accept the terms and conditions.");
            return;
        }

        const idNumber = document.getElementById("idNumber").value;
        const phoneNumber = document.getElementById("phoneNumber").value;
        if (!idNumber || !phoneNumber) {
            alert("Please fill in all required fields.");
            return;
        }

        const houseId = document.getElementById("houseId").value;

        simulatePaymentProcess(houseId, idNumber, phoneNumber);
    }

    function simulatePaymentProcess(houseId, idNumber, phoneNumber) {
        showLoadingAnimation();
        setTimeout(() => {
            fetch('/house/rent/' + houseId, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ idNumber: idNumber, phoneNumber: phoneNumber })
            })
            .then(response => response.json())
            .then(data => {
                hideLoadingAnimation();
                if (data.success) {
                    closeModal();
                } else {
                    alert("Payment failed. Please try again.");
                }
            });
        }, 4000);
    }

    function showLoadingAnimation() {
        loadingAnimation.style.display = "flex";
    }

    function hideLoadingAnimation() {
        loadingAnimation.style.display = "none";
    }



    window.updateBookings = function(event) {
        const increment = event.target.checked ? 1 : -1;
        const houseId = document.getElementById("houseId2").value;
        fetch('/house/update-bookings/' + houseId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ increment: increment })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                console.log("Bookings updated successfully.");
            } else {
                alert("Failed to update bookings. Please try again.");
            }
        });
    }
});
