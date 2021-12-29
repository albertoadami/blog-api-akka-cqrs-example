package it.adami.blog.actor.akka

import java.time.LocalDateTime

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior}
import it.adami.blog.actor.akka.command.UserCommand
import it.adami.blog.actor.akka.command.UserCommand.CreateUserCommand
import it.adami.blog.actor.akka.event.{CreatedUserEvent, UserEvent}
import it.adami.blog.actor.akka.state.{State, UserState}

object UserBehavior {

  private def commandHandler(context: ActorContext[UserCommand]): (State[UserState], UserCommand) => Effect[UserEvent, State[UserState]] = { (state, cmd) => cmd match {
    case cmd: CreateUserCommand if state.value.isEmpty  =>
      context.log.info("Received CreateUserCommand cmd {}", cmd)
      val event = CreatedUserEvent(
        userName = cmd.username,
        firstName = cmd.firstName,
        lastName = cmd.firstName,
        email = cmd.email,
        password = cmd.password,
        dateOfBirth = cmd.dateOfBirth,
        gender = cmd.gender,
        creationDate = LocalDateTime.now()
      )
      Effect.persist(event)
    }
  }

  private val eventHandler: (State[UserState], UserEvent) => State[UserState] = { (state, event) => event match {
    case evt: CreatedUserEvent =>
      val userState = UserState(
        userName = evt.userName,
        firstName = evt.firstName,
        lastName = evt.lastName,
        email = evt.email,
        password = evt.password,
        dateOfBirth = evt.dateOfBirth,
        gender = evt.gender,
        creationDate = evt.creationDate,
        lastUpdateDate = None,
        active = false
      )
      state.copy(Some(userState))
  }

  }

  def apply(persistenceId: PersistenceId): Behavior[UserCommand] =
    Behaviors.setup { context =>
      EventSourcedBehavior[UserCommand, UserEvent, State[UserState]](
        persistenceId = persistenceId,
        emptyState = State[UserState](None),
        commandHandler = commandHandler(context),
        eventHandler = eventHandler)
    }
}