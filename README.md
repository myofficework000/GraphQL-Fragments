# 🚀 GraphQL Fragments Android Sample

A clean Android sample demonstrating **how to use GraphQL Fragments properly** in a real-world architecture.

This project explains:

✔ Why fragments exist  
✔ How they prevent duplicate queries  
✔ How they improve scalability  
✔ How to integrate them in Android using Apollo GraphQL  
✔ How to structure production-level code

---

## 📱 App Overview
This app fetches SpaceX launches using GraphQL and reuses UI models across multiple queries using **Fragments**.

Instead of repeating fields in multiple queries, we define them once and reuse them everywhere.

> Write once → Reuse everywhere → Safer API evolution

---

## 🧠 Why GraphQL Fragments?

Without fragments:

- Large duplicated queries
- Hard to maintain
- Easy to break UI when backend changes

With fragments:

- Single source of truth for fields
- Cleaner architecture
- Safer refactoring
- Smaller network payload
- Better scalability

---

## 🏗 Tech Stack

| Layer | Technology |
|------|------|
| Language | Kotlin |
| Architecture | MVVM |
| Network | GraphQL |
| Client | Apollo Android |
| Async | Coroutines |
| UI | Jetpack Compose |
| API | SpaceX GraphQL |

---

## 📂 Project Structure

```

com.example.graphql
│
├── data
│   ├── remote
│   │   ├── ApolloClientProvider.kt
│   │   ├── LaunchRepository.kt
│
├── domain
│   ├── model
│
├── ui
│   ├── launchlist
│   ├── launchdetails
│
└── graphql
├── fragments
│   └── LaunchInfo.graphql
├── queries
│   ├── LaunchListQuery.graphql
│   └── LaunchDetailsQuery.graphql

````

---

## ⚙️ Step 1 — Add Dependencies

```gradle
plugins {
    id("com.apollographql.apollo3") version "3.8.2"
}

dependencies {
    implementation("com.apollographql.apollo3:apollo-runtime:3.8.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
````

---

## ⚙️ Step 2 — Configure Apollo

```gradle
apollo {
    service("service") {
        packageName.set("com.example.graphql")
    }
}
```

---

## ⚙️ Step 3 — Create GraphQL Fragment

📄 `LaunchInfo.graphql`

```graphql
fragment LaunchInfo on Launch {
  id
  name
  date_utc
  success
}
```

This becomes reusable across queries.

---

## ⚙️ Step 4 — Use Fragment in Query

📄 `LaunchListQuery.graphql`

```graphql
query LaunchListQuery {
  launchesPast(limit: 10) {
    ...LaunchInfo
  }
}
```

📄 `LaunchDetailsQuery.graphql`

```graphql
query LaunchDetailsQuery($id: ID!) {
  launch(id: $id) {
    ...LaunchInfo
    details
  }
}
```

👉 Notice: no duplicate fields written again.

---

## ⚙️ Step 5 — Apollo Client

```kotlin
object ApolloClientProvider {

    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://api.spacex.land/graphql/")
        .build()
}
```

---

## ⚙️ Step 6 — Repository Layer

```kotlin
class LaunchRepository {

    suspend fun getLaunches(): List<LaunchInfo> {
        val response = ApolloClientProvider.apolloClient
            .query(LaunchListQuery())
            .execute()

        return response.data?.launchesPast?.mapNotNull {
            it?.fragments?.launchInfo
        } ?: emptyList()
    }
}
```

---

## ⚙️ Step 7 — ViewModel

```kotlin
class LaunchViewModel : ViewModel() {

    private val repository = LaunchRepository()

    val launches = liveData {
        emit(repository.getLaunches())
    }
}
```

---

## ⚙️ Step 8 — UI Usage

```kotlin
Text(text = launch.name)
Text(text = launch.date_utc)
```

No mapping required because fragment already acts as UI model.

---

## 💡 Key Learning

Fragments behave like **shared DTO contracts** between queries.

Instead of:

```
Query A fields
Query B fields
Query C fields
```

We now have:

```
Shared Fragment → Used Everywhere
```

---

## 🧪 When to Use Fragments

Use fragments when:

* Same UI model appears in multiple screens
* Same API fields used in multiple queries
* App grows beyond 3 screens
* Backend changes frequently

Avoid fragments when:

* Very small prototype app
* One-time query

---

## 📊 Benefits Achieved

| Problem           | Solution           |
| ----------------- | ------------------ |
| Duplicate queries | Fragments          |
| Breaking UI       | Shared contract    |
| Hard refactoring  | Centralized fields |
| API evolution     | Safe changes       |

---

## 🎯 What You Learn From This Repo

* Real GraphQL architecture
* Clean Android integration
* Production-level structure
* Proper fragment usage
* Maintainable network layer

---

## 🚀 Future Improvements

* Pagination
* Mutations
* Caching
* Offline support
* Multi-module GraphQL

---

## 🤝 Contribution

Feel free to fork and experiment with new queries or mutations.

---

## ⭐ If this helped you

Give the repo a star — it helps others discover clean GraphQL architecture.

---

## 👨‍💻 Author

Android Engineer passionate about scalable architecture and clean networking layers.
