#include <iostream>
#include <vector>
#include <string>
#include <cmath>
#include <iomanip>

// Структура за фиксиран разход
struct Expense {
    std::string name;
    double amount;
};

// Функция за изчисление на инфлационното обезценяване (покупателна способност)
double calculateInflationImpact(double currentAmount, double annualInflationRate, int years) {
    return currentAmount / std::pow((1.0 + annualInflationRate / 100.0), years);
}

int main() {
    // Настройка за кирилица в конзолата (ако е необходимо)
    std::setlocale(LC_ALL, "Bulgarian");

    // Входни данни от потребителя (симулация на профила)
    double monthlyIncome = 1500.0; // Заплата в евро
    int age = 20;
    std::string city = "София";

    // Списък с фиксирани разходи (Наем, транспорт, храна, застраховка)
    std::vector<Expense> fixedCosts = {
        {"🏠 Наем / Имот", 450.0},
        {"🚗 Транспорт (Гориво)", 130.0},
        {"🛒 Храна & Супермаркет", 280.0},
        {"🛡️ Застраховка KBC", 12.0}
    };

    std::cout << "=== FINSTART C++ BACKEND ENGINE ===" << std::endl;
    std::cout << "Младежки профил: Възраст " << age << ", Град: " << city << std::endl;
    std::cout << "Месечен доход: " << monthlyIncome << " EUR\n" << std::endl;

    // Изчисляване на общите фиксирани разходи
    double totalFixed = 0.0;
    for (const auto& expense : fixedCosts) {
        totalFixed += expense.amount;
        std::cout << expense.name << ": " << expense.amount << " EUR" << std::endl;
    }

    if (totalFixed >= monthlyIncome) {
        std::cout << "\n[ВНИМАНИЕ] Фиксираните разходи надвишават доходите!" << std::endl;
        return 1;
    }

    // ИИ логика за разпределение на остатъка
    double leftover = monthlyIncome - totalFixed;
    double aiSavings = std::round(leftover * 0.35); // 35% за спестовен фонд/цели
    double aiPersonal = leftover - aiSavings;       // Чисти пари за лични нужди

    std::cout << "-----------------------------------" << std::endl;
    std::cout << "Общо фиксирани разходи: " << totalFixed << " EUR" << std::endl;
    std::cout << "Свободен остатък: " << leftover << " EUR" << std::endl;
    std::cout << "🛡️ ИИ Спестовен фонд (35%): " << aiSavings << " EUR" << std::endl;
    std::cout << "🎉 Чисти пари за ЛИЧНИ НЕЩА (65%): " << aiPersonal << " EUR" << std::endl;
    std::cout << "-----------------------------------" << std::endl;

    // Симулация на инфлация върху спестяванията за 3 години при 4.5% инфлация
    double annualInflation = 4.5;
    int projectionYears = 3;
    double totalSavedYearly = aiSavings * 12;

    std::cout << "\n--- ИИ СИМУЛАТОР НА ИНФЛАЦИЯ ЗА СПЕСТЯВАНИЯТА ---" << std::endl;
    for (int y = 1; y <= projectionYears; ++y) {
        double futureValue = calculateInflationImpact(totalSavedYearly * y, annualInflation, y);
        std::cout << "След " << y << " год. натрупаните спестявания (" << (totalSavedYearly * y) 
                  << " EUR) ще имат реална покупателна стойност от ~" << std::round(futureValue) << " EUR." << std::endl;
    }

    return 0;
}