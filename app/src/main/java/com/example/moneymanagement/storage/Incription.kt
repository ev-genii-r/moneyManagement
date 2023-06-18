package com.example.moneymanagement.storage

class Incription {

    // Шифрование данных с использованием алгоритма DES
    fun encryptDES(data: Long, key: Long): Long {
        val subKeys = generateSubKeys(key) // Генерация подключей
        var permutedData = initialPermutation(data) // Начальная перестановка данных

        var left = permutedData ushr 32 // Левая половина данных
        var right = permutedData and 0xFFFFFFFFL // Правая половина данных

        for (i in 0 until 16) {
            val temp = left
            left = right
            right = temp xor feistelFunction(right, subKeys[i]) // Применение функции Фейстеля
        }

        permutedData = (right shl 32) or left // Объединение левой и правой половин данных
        return finalPermutation(permutedData) // Конечная перестановка данных
    }

    // Расшифрование данных с использованием алгоритма DES
    fun decryptDES(data: Long, key: Long): Long {
        val subKeys = generateSubKeys(key) // Генерация подключей
        var permutedData = initialPermutation(data) // Начальная перестановка данных

        var left = permutedData ushr 32 // Левая половина данных
        var right = permutedData and 0xFFFFFFFFL // Правая половина данных

        for (i in 15 downTo 0) {
            val temp = left
            left = right
            right = temp xor feistelFunction(right, subKeys[i]) // Применение функции Фейстеля в обратном порядке
        }

        permutedData = (right shl 32) or left // Объединение левой и правой половин данных
        return finalPermutation(permutedData) // Конечная перестановка данных
    }

    // Генерация подключей
    private fun generateSubKeys(key: Long): Array<Long> {
        val subKeys = arrayOfNulls<Long>(16)
        var permutedKey = permuteKey(key) // Перестановка ключа

        var left = permutedKey ushr 28 // Левая половина ключа
        var right = permutedKey and 0xFFFFFFF // Правая половина ключа

        for (i in 0 until 16) {
            val KEY_SHIFTS = listOf<Int>() // Список сдвигов для каждого раунда
            left = circularLeftShift(left, KEY_SHIFTS[i]) // Циклический сдвиг влево левой половины ключа
            right = circularLeftShift(right, KEY_SHIFTS[i]) // Циклический сдвиг влево правой половины ключа
            val combinedKey = (left shl 28) or right // Объединение левой и правой половин ключа
            subKeys[i] = permuteKey(combinedKey) // Перестановка подключей
        }

        return subKeys.requireNoNulls() // Возвращение массива подключей
    }

    // Перестановка ключа
    private fun permuteKey(key: Long): Long {
        var permutedKey = 0L
        for (i in 0 until 56) {
            val PC1 = listOf<Int>() // Начальная перестановка ключа
            val bitPosition = PC1[i] - 1 // Позиция бита в ключе
            val bitValue = (key ushr bitPosition) and 1L // Значение бита
            permutedKey = (permutedKey shl 1) or bitValue // Добавление бита к перестанованному ключу
        }
        return permutedKey // Возвращение перестанованного ключа
    }

    // Начальная перестановка данных
    private fun initialPermutation(data: Long): Long {
        val IP = listOf<Int>() // Начальная перестановка данных
        var permutedData = 0L
        for (i in 0 until 64) {
            val bitPosition = IP[i] - 1 // Позиция бита в данных
            val bitValue = (data ushr bitPosition) and 1L // Значение бита
            permutedData = (permutedData shl 1) or bitValue // Добавление бита к перестанованным данным
        }
        return permutedData // Возвращение перестанованных данных
    }

    // Конечная перестановка данных
    private fun finalPermutation(data: Long): Long {
        val FP = listOf<Int>() // Конечная перестановка данных
        var permutedData = 0L
        for (i in 0 until 64) {
            val bitPosition = FP[i] - 1 // Позиция бита в данных
            val bitValue = (data ushr bitPosition) and 1L // Значение бита
            permutedData = (permutedData shl 1) or bitValue // Добавление бита к перестанованным данным
        }
        return permutedData // Возвращение перестанованных данных
    }

    // Функция Фейстеля
    private fun feistelFunction(data: Long, key: Long): Long {
        val expandedData = expand(data) // Расширение данных
        val xoredData = expandedData xor key // XOR между расширенными данными и ключом
        val substitutedData = substitute(xoredData) // Замена данных
        return permute(substitutedData) // Перестановка данных
    }

    // Расширение данных
    private fun expand(data: Long): Long {
        val E = listOf<Int>() // Расширение данных
        var expandedData = 0L
        for (i in 0 until 48) {
            val bitPosition = E[i] - 1 // Позиция бита в данных
            val bitValue = (data ushr bitPosition) and 1L // Значение бита
            expandedData = (expandedData shl 1) or bitValue // Добавление бита к расширенным данным
        }
        return expandedData // Возвращение расширенных данных
    }

    // Замена данных
    private fun substitute

                (data: Long): Long {
        val S_BOX = listOf<List<List<Int>>>() // Таблицы замен
        var substitutedData = 0L
        for (i in 0 until 8) {
            val row = ((data and (0x20L or (0x1L shl 4))) ushr (4 + i * 6)) or ((data and 1L) shl 1) // Выбор строки в таблице замен
            val column = (data ushr (i * 6 + 1)) and 0xF // Выбор столбца в таблице замен
            substitutedData = (substitutedData shl 4) or S_BOX[i][row.toInt()][column.toInt()].toLong() // Добавление замененных данных
        }
        return substitutedData // Возвращение замененных данных
    }

    // Перестановка данных
    private fun permute(data: Long): Long {
        val P = listOf<Int>() // Перестановка данных
        var permutedData = 0L
        for (i in 0 until 32) {
            val bitPosition = P[i] - 1 // Позиция бита в данных
            val bitValue = (data ushr bitPosition) and 1L // Значение бита
            permutedData = (permutedData shl 1) or bitValue // Добавление бита к перестанованным данным
        }
        return permutedData // Возвращение перестанованных данных
    }

    // Циклический сдвиг влево
    private fun circularLeftShift(value: Long, shift: Int): Long {
        val shiftedValue = (value shl shift) or (value ushr (28 - shift)) // Циклический сдвиг влево значения
        return shiftedValue and 0xFFFFFFF // Ограничение значения до 28 бит
    }

}