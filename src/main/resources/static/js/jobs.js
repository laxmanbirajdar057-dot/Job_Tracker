async function loadJobs() {

    const response = await fetch('/api/jobs');

    const jobs = await response.json();

    let rows = "";

    jobs.forEach(job => {
        rows += `
            <tr>
                <td>${job.id}</td>
                <td>${job.company}</td>
                <td>${job.role}</td>
            </tr>
        `;
    });

    document.getElementById("jobTable").innerHTML = rows;
}

async function addJob() {

    const company = document.getElementById("company").value;
    const role = document.getElementById("role").value;

    const job = {
        company: company,
        role: role
    };

    await fetch('/api/jobs', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(job)
    });

    loadJobs();
}