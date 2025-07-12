# 📚 Course Search Application

A Spring Boot application integrated with Elasticsearch to provide powerful **autocomplete** and **fuzzy search** features for course titles.

---

## 🚀 1. Launch Elasticsearch with Docker

Make sure Docker is installed. Then run:

```bash
docker-compose up -d
```

This will start Elasticsearch on `localhost:9200`.

---

## 🛠️ 2. Build and Run the Spring Boot Application

```bash
# Build the app
mvn clean package

# Run the app
java -jar target/course-search-0.0.1-SNAPSHOT.jar
```

The app will start on `http://localhost:8080`.

---

## 🧩 3. Populate Sample Data

Sample data will be automatically loaded on startup from `resources/sample-courses.json`.

Make sure the `DataLoader` class is annotated with `@Component` and implements `CommandLineRunner`.

---

## 🔎 4. Features

### 4.1 🔤 Autocomplete (Completion Suggester)

#### ✅ Index Setup

Each course document has a `suggest` field using Elasticsearch’s `completion` type.

Example mapping:

```json
{
  "mappings": {
    "properties": {
      "title": { "type": "text" },
      "suggest": { "type": "completion" }
    }
  }
}
```

#### ✅ Autocomplete Endpoint

```http
GET /api/search/suggest?q=phy
```

Example Response:

```json
["Physics 101", "Physical Chemistry", "Physiology Basics"]
```

---

### 4.2 🧠 Fuzzy Matching

Search even with typos! For example, searching for `"dinors"` matches `"Dinosaurs 101"`.

#### ✅ Fuzzy Search Endpoint

```http
GET /api/search?q=dinors
```

Example Response:

```json
[
  {
    "id": "1",
    "title": "Dinosaurs 101",
    "description": "An introduction to dinosaurs."
  }
]
```

---

## 📮 5. API Summary

| Endpoint | Description |
|----------|-------------|
| `GET /api/search?q=keyword` | Full-text fuzzy search |
| `GET /api/search/suggest?q=prefix` | Autocomplete suggestions based on prefix |

---

## 🧪 6. Example `curl` Commands

```bash
# Search with typo
curl "http://localhost:8080/api/search?q=dinors"

# Get autocomplete suggestions
curl "http://localhost:8080/api/search/suggest?q=phy"
```
---

## 📂 Folder Structure

```
course-search/
├── src/main/java/com/example/course_search/
│   ├── controller/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── CourseSearchApplication.java
├── src/main/resources/sample-courses.json
├── docker-compose.yml
├── pom.xml
└── README.md
```

---
