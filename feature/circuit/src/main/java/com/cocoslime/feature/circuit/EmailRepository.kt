package com.cocoslime.feature.circuit


object EmailRepository {
    fun getEmails() = listOf(
        Email(
            emailId = "1",
            subject = "Meeting re-sched!",
            body = "Hey, I'm going to be out of the office tomorrow. Can we reschedule?",
        )
    )

    fun getEmail(emailId: String): Email {
        return getEmails().firstOrNull { it.emailId == emailId }
            ?: throw IllegalArgumentException("Email not found")
    }
}
