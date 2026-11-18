
async function findInternships() {
    const skills = document.getElementById("skills").value.trim();
    const results = document.getElementById("results");
    const status = document.getElementById("status");
    const button = document.getElementById("searchBtn");

    if (!skills) {
        status.textContent = "Please enter your skills first.";
        return;
    }

    results.replaceChildren();
    status.textContent = "Finding the best internships for you...";
    button.disabled = true;

    try {
        const url = "/api/recommendations?skills="
            + encodeURIComponent(skills);

        const response = await fetch(url);

        if (!response.ok) {
            throw new Error("API error: " + response.status);
        }

        const internships = await response.json();

        status.textContent = "";

        if (internships.length === 0) {
            results.textContent =
                "No internships found. Please add internships first.";
            return;
        }

        internships.forEach(internship => {
            const card = document.createElement("div");
            card.className = "internship-card";

            const company = document.createElement("div");
            company.className = "company";
            company.textContent = internship.company;

            const title = document.createElement("h3");
            title.textContent = internship.title;

            const location = document.createElement("p");
            location.className = "location";
            location.textContent = "Location: " + internship.location;

            const skillsText = document.createElement("p");
            skillsText.className = "skills-text";
            skillsText.textContent =
                "Required skills: " + internship.requiredSkills;

            const score = document.createElement("div");
            score.className = "match-score";
            score.textContent =
                Number(internship.score).toFixed(2) + "% Match";

            card.append(
                company,
                title,
                location,
                skillsText,
                score
            );

            results.appendChild(card);
        });

    } catch (error) {
        console.error(error);

        status.textContent =
            "Unable to load recommendations. Check that Spring Boot and Python are running.";

    } finally {
        button.disabled = false;
    }
}

document.getElementById("skills").addEventListener("keydown", event => {
    if (event.key === "Enter") {
        findInternships();
    }
});
