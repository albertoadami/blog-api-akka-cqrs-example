package it.adami.blog.actor.akka

import java.time.LocalDate

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior}
import it.adami.blog.actor.akka.command.UserCommand
import it.adami.blog.actor.akka.command.UserCommand.CreateUserCommand
import it.adami.blog.actor.akka.event.{CreatedUserEvent, UserEvent}
import it.adami.blog.actor.akka.result.{UserCreated, UsernameAlreadyInUse}
import it.adami.blog.actor.akka.state.{State, UserState}
import it.adami.blog.model.UserId

object UserActor {

  private def commandHandler(
      context: ActorContext[UserCommand]
  ): (State[UserState], UserCommand) => Effect[UserEvent, State[UserState]] = { (state, cmd) =>
    cmd match {
      case cmd: CreateUserCommand if state.value.isEmpty =>
        context.log.info("Received cmd {}", cmd)
        val event = CreatedUserEvent(
          userName = cmd.username,
          firstName = cmd.firstName,
          lastName = cmd.firstName,
          email = cmd.email,
          password = cmd.password,
          dateOfBirth = cmd.dateOfBirth,
          gender = cmd.gender,
          creationDate = LocalDate.now()
        )
        Effect.persist(event).thenRun { _ => cmd.replyTo ! UserCreated(UserId(cmd.username)) }
      case cmd: CreateUserCommand =>
        Effect.none.thenRun(_ => cmd.replyTo ! UsernameAlreadyInUse(UserId(cmd.username)))

    }
  }

  private val eventHandler: (State[UserState], UserEvent) => State[UserState] = { (state, event) =>
    event match {
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
        eventHandler = eventHandler
      )
    }
}
