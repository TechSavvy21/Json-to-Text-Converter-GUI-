# Json to Text Converter

A simple (Because I'm lazy) **Java Swing** desktop application that imports JSON files, converts them into plain text for easier reading, and allows exporting the result.
Built with [`org.json`](https://stleary.github.io/JSON-java/) for JSON parsing.

---

## 📸 Screenshot

```md
![JsonTextConverter Screenshot](screenshot.png)
```

---

## ✨ Features

* :ballot_box:  **Import JSON** from file
* :pencil: **View and edit** JSON content in a text area
* :repeat: **Convert JSON** into a readable plain text format
* :floppy_disk:  **Export results** to a text file
* :bulb: **Help & About** menus for quick guidance

---

## 🛠 Requirements

* :coffee: **Java 21+**
* :books: [`org.json`](https://stleary.github.io/JSON-java/) library (JSON-java)

---

## 📦 Installation

**Using Maven**

```xml
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20240303</version>
</dependency>
```

**Manual JAR**

1. Download [`json.jar`](https://mvnrepository.com/artifact/org.json/json).
2. Add it to your classpath:

   ```bash
   javac -cp json.jar JsonTextConverter.java
   java -cp .:json.jar JsonTextConverter
   ```

---

## 🚀 Usage

1. Run the application:

   ```bash
   java -cp .:json.jar JsonTextConverter
   ```
2. Click **File → Import** to open a JSON file.
3. Click **Convert** to format it into plain text.
4. Click **File → Export** to save the output.

---

## 📂 Project Structure

```
src/
└── me/techsavvy/
    └── JsonTextConverter.java
```

---

## 🧪 Example

**Input JSON:**

```json
{
  "name": "Alice",
  "age": 30,
  "skills": ["Java", "Python", "JavaScript"]
}
```

**Converted Output:**

```
name: Alice
age: 30
skills: Java, Python, JavaScript
```

---

## ⚠️ Known Issues

* Large JSON files may cause the UI to freeze temporarily.
* Malformed JSON will show an error dialog.

---

## 📜 License

This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

---
