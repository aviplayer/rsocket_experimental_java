import React from "react";
import {useSelector} from "react-redux";
import {getMessages} from "../state/app/app.selectors";
import {MessageContent, MessageDate, Messages, MessageText} from "../components";

export const AppContainer: React.FC = React.memo(() => {
  const messages = useSelector(getMessages);
  return (
    <Messages>
      {messages.map((message, i) => (
          <MessageContent key={i}>
            <MessageText>{message}</MessageText>
            <MessageDate>{new Date().getUTCMilliseconds()}</MessageDate>
          </MessageContent>
        )
      )
      }
    </Messages>
  );

});
