
console.log("auth.js loaded");

async function login() {

    console.log("Login button clicked");

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    console.log(email);

    const response = await fetch("/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email,
            password
        })
    });

    console.log("Status:", response.status);

    const data = await response.json();

    console.log(data);

    localStorage.setItem("token", data.token);

    alert("Login Success");
}