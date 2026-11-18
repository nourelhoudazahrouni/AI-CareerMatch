from fastapi import FastAPI
from pydantic import BaseModel
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

app = FastAPI(title="AI CareerMatch Service")


class Internship(BaseModel):
    id: int
    title: str
    company: str
    location: str
    requiredSkills: str


class MatchRequest(BaseModel):
    skills: str
    internships: list[Internship]


@app.get("/")
def home():
    return {"message": "AI CareerMatch AI Service is running"}


@app.post("/match")
def match_internships(request: MatchRequest):

    if not request.internships:
        return []

    documents = [request.skills] + [
        internship.requiredSkills
        for internship in request.internships
    ]

    if not any(document.strip() for document in documents):
        return [
            {
                **internship.model_dump(),
                "score": 0.0
            }
            for internship in request.internships
        ]

    vectorizer = TfidfVectorizer()
    vectors = vectorizer.fit_transform(documents)

    scores = cosine_similarity(
        vectors[0:1],
        vectors[1:]
    )[0]

    results = []

    for internship, score in zip(request.internships, scores):
        results.append({
            **internship.model_dump(),
            "score": round(float(score) * 100, 2)
        })

    results.sort(
        key=lambda item: item["score"],
        reverse=True
    )

    return results
