# 🏡 Otodom Price Monitor Bot
### A **Telegram bot** for monitoring real estate prices on [Otodom.pl](https://www.otodom.pl/) 🏠📉📈  

---

## 📌 Features
✅ Track real estate prices on Otodom.pl  
✅ Get a list of all tracked properties using `/all`  
✅ Automatic price monitoring and notifications  
✅ Remove a tracked property using `/remove [URL]`  
✅ Secure data storage with **PostgreSQL**  
✅ Easy deployment with **Docker**  

---

## 🚀 How It Works
1️⃣ **Start the bot** → `/start`  
2️⃣ **Send a property link** → Bot saves it for tracking  
3️⃣ **Check your tracked properties** → `/all`  
4️⃣ **Remove a property from tracking** → `/remove [URL]`  
5️⃣ **Receive alerts** when a price changes  

Example usage:  
```
/start
https://www.otodom.pl/pl/oferta/12345678
/all
/remove https://www.otodom.pl/pl/oferta/12345678
```

## 📥 Installation
### 🔹 1. Clone the Repository
```sh
git clone https://github.com/yourusername/otodom-price-bot.git
cd otodom-price-bot
```

### 🔹 2. Configure the Bot
Create a `.env` file or update `application.yml` with your bot credentials.

```yml

  datasource:
    url: jdbc:postgresql://localhost:5432/otodom
    username: your_postgres_user
    password: your_postgres_password
    driver-class-name: org.postgresql.Driver

telegram:
  bot:
    token: YOUR_TELEGRAM_BOT_TOKEN
    username: YOUR_TELEGRAM_BOT_USERNAME

```

### 🔹 3. Run PostgreSQL (If Not Running)
If you don't have PostgreSQL installed, use Docker:
```sh
docker run --name postgres-otodom -e POSTGRES_USER=your_postgres_user -e POSTGRES_PASSWORD=your_postgres_password -e POSTGRES_DB=otodom -p 5432:5432 -d postgres
```

### 🔹 4. Build & Run the Bot
```sh
mvn clean install
mvn spring-boot:run
```

---

## 📜 Available Commands
| Command   | Description |
|-----------|------------|
| `/start`  | Start the bot |
| `/all`    | Get a list of all tracked properties |
| `[URL]`           | Send a property link to start tracking |
| `/remove [URL]`   | Stop tracking the link |

---






