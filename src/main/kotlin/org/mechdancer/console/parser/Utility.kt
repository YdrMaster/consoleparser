package org.mechdancer.console.parser

import org.mechdancer.console.token.TokenType.*
import org.mechdancer.console.token.TokenType.Number

/** 取出句子中所有整数 */
val Sentence.integers
	get() = filter { it.type == Integer }.map { it.data as Int }

/** 取出句子中所有数字 */
val Sentence.numbers
	get() =
		mapNotNull {
			when (it.type) {
				Integer -> (it.data as Int).toDouble()
				Number  -> it.data as Double
				Sign, Word, Note, Key
				        -> null
			}
		}

/** 取出句子中所有符号 */
val Sentence.signs
	get() = filter { it.type == Sign }.map { it.data as String }

/** 取出句子中所有单词 */
val Sentence.words
	get() = filter { it.type == Word }.map { it.data as String }

/** 取出句子中所有关键字 */
val Sentence.keys
	get() = filter { it.type == Key }.map { it.data }

/** 构造解析器 */
fun buildParser(block: Parser.() -> Unit) =
	Parser().apply(block)

///** 显示指令反馈 */
//fun display(feedback: Pair<Boolean, String>) =
//	(if (feedback.first) System.out else System.err)
//		.println(feedback.second)
//
////组织反馈信息
//fun feedback(sentence: Sentence, result: Result) =
//	result.positive to when (result.status) {
//		Result.Status.Success,
//		Result.Status.Failure    -> result.data
//		Result.Status.Error      -> buildString {
//			append("invalid command: ")
//			sentence.map { it.text }
//				.forEachIndexed { i, text ->
//					append(if (i == result.what) "> $text < " else "$text ")
//				}
//			result.message
//				.takeIf(String::isNotBlank)
//				?.let { append("\n$it") }
//		}
//		Result.Status.Incomplete -> buildString {
//			append("incomplete command: ")
//			sentence.forEach { append("${it.text} ") }
//			append("...")
//			result.message
//				.takeIf(String::isNotBlank)
//				?.let { append("\n$it") }
//		}
//	}