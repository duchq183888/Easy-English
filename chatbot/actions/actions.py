# This files contains your custom actions which can be used to run
# custom Python code.
#
# See this guide on how to implement these action:
# https://rasa.com/docs/rasa/custom-actions


# This is a simple example for a custom action which utters "Hello World!"

from typing import Any, Text, Dict, List

from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher
import openai
openai.api_key = "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"
import random
import requests

class ActionTopic(Action):

    def name(self) -> Text:
        return "action_topic"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        topics = ["Hôm nay chúng ta sẽ học từ vựng tiếng Anh về chủ đề \"Thiên nhiên\" (Nature). Dưới đây là một số từ vựng bạn có thể tìm hiểu:\n1. Forest(n.): rừng\n2. Mountain(n.): núi\n3. River(n.): sông\n4. Lake(n.): hồ\n5. Ocean(n.): đại dương",
                  "Hôm nay chúng ta sẽ học từ vựng tiếng Anh về chủ đề \"Địa điểm du lịch\" (Travel Destinations). Dưới đây là một số từ vựng bạn có thể tìm hiểu:\n1. Beach(n.): bãi biển\n2. Mountain(n.): núi\n3. City(n.): thành phố\n4. Island(n.): đảo\n5. Resort(n.): khu nghỉ dưỡng",
                  "Hôm nay chúng ta sẽ học từ vựng tiếng Anh về chủ đề \"Công việc\" (Jobs/Careers). Dưới đây là một số từ vựng bạn có thể tìm hiểu:\n1. Scientist(n.): nhà khoa học\n2. Entrepreneur(n.): doanh nhân\n3. Translator(n.): dịch giả\n4. Photographer(n.): nhiếp ảnh gia\n5. Electrician(n.): thợ điện",
                  "Hôm nay chúng ta sẽ học từ vựng tiếng Anh về chủ đề \"Thể thao\" (Sports). Dưới đây là một số từ vựng bạn có thể tìm hiểu:\n1. Cricket(n.): bóng chày Anh\n2. Table tennis(n.): bóng bàn\n3. Badminton(n.): cầu lông\n4. Martial arts(n.): môn võ thuật\n5. Sailing(n.): lướt ván",
                  "Hôm nay chúng ta sẽ học từ vựng tiếng Anh về chủ đề \"Gia đình\" (Family). Dưới đây là một số từ vựng bạn có thể tìm hiểu:\n1. Parent(n.): bố mẹ\n2. Siblings(n.): anh chị em\n3. Grandparents(n.): ông bà\n4. Relatives(n.): họ hàng\n",
                  "Hôm nay chúng ta sẽ học từ vựng tiếng Anh về chủ đề \"Công nghệ\" (Technology). Dưới đây là một số từ vựng bạn có thể tìm hiểu:\n1. smartphone(n.): điện thoại thông minh\n2. Computer(n.): máy tính\n3. Internet(n.): mạng internet\n4. Software(n.): phần mềm\n",
                  "Hôm nay chúng ta sẽ học từ vựng tiếng Anh về chủ đề \"Môi trường\" (Environment). Dưới đây là một số từ vựng bạn có thể tìm hiểu:\n1. Climate change(n.): biến đổi khí hậu\n2. Renewable energy(n.): năng lượng tái tạo\n3. Recycling(n.): tái chế\n4. Conservation(n.): bảo tồn\n5. Pollution(n.): sự ô nhiễm"]
        x = random.randint(0,len(topics)-1)

        dispatcher.utter_message(text=topics[x])

        return []
    
def call_chatGPT(input_text):
     completion = openai.ChatCompletion.create(
     model="gpt-4-0314",
    #  max_tokens=1024,
    #  temperature=0.7,
    #  n=1,
     messages=[
       {"role": "user", 
        "content": input_text}
         ]
      )

    #  print(completion.choices[0].message.content)

     answer = completion.choices[0].message.content

     return answer

class ActionMeaning(Action):
    
    def name(self) -> Text:
        return "action_mean"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        

        vocab1 = str(next(tracker.get_latest_entity_values("vocab"), None))
        if vocab1!="None":
            print("mean :"+vocab1)
            rs = call_chatGPT("Nghĩa của từ này trong tiếng Anh là gì, và nó là từ loại gì: "+ vocab1)
            dispatcher.utter_message(text=rs)
        else:
            print("ndetect")
            user_input = tracker.latest_message['text']
            res = call_chatGPT(user_input+"(Trong tiếng Anh), từ loại của nó nữa nhé")
            dispatcher.utter_message(text=res)
    
        return []
    
class ActionExample(Action):
    
    def name(self) -> Text:
        return "action_example"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        ex = str(next(tracker.get_latest_entity_values("vocab"), None))

        if ex!="None":
            print("example :"+ex)
            rs = call_chatGPT("Lấy cho tôi 1 ví dụ về từ "+ ex + "(1 ví dụ bằng tiếng Anh và nghĩa của nó)")
            dispatcher.utter_message(text=rs)
        else:
            print("ndetect")
            user_input = tracker.latest_message['text']
            res = "Của bạn đây ạ:\n"+call_chatGPT(user_input+"(1 ví dụ bằng tiếng Anh và nghĩa của nó)" )
            dispatcher.utter_message(text=res)
    
    
        return []

    
class ActionSynonym(Action):
    
    def name(self) -> Text:
        return "action_synonym"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        syno = str(next(tracker.get_latest_entity_values("vocab"), None))

        if syno!="None":
            print("synonym :"+syno)
            rs = call_chatGPT("Từ đồng nghĩa của "+ syno)
            dispatcher.utter_message(text="Đây là một số từ đồng nghĩa của nó nha:\n"+rs)
        else:
            print("ndetect")
            user_input = tracker.latest_message['text']
            res = call_chatGPT(user_input+"(Trong tiếng Anh)")
            dispatcher.utter_message(text="Đây là một số từ đồng nghĩa của nó nha:\n"+res)
    
        return []

class ActionCustomTopic(Action):
    
    def name(self) -> Text:
        return "action_custom_topic"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        syno = str(next(tracker.get_latest_entity_values("vi-vocab"), None))

        if syno!="None":
            print("custom_topic :"+syno)
            rs = call_chatGPT("Cho tôi 1 vài từ vựng tiếng anh về chủ đề: "+ syno)
            dispatcher.utter_message(text=rs)
        else:
            print("ndetect")
            user_input = tracker.latest_message['text']
            res = call_chatGPT(user_input+ ", hãy liệt kê cho tôi 5 từ vựng tiếng Anh")
            dispatcher.utter_message(text="Tất nhiên rồi!\n"+res)
    
        return []

class ActionPronounce(Action):
    
    def name(self) -> Text:
        return "action_pronounce"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:
        
        syno = str(next(tracker.get_latest_entity_values("vi-vocab"), None))

        if syno!="None":
            print("pro :"+syno)
            rs = call_chatGPT(syno+" phát âm như thế nào nhỉ, mô tả chi tiết giúp tôi")
            dispatcher.utter_message(text=rs)
        else:
            print("ndetect")
            user_input = tracker.latest_message['text']
            res = call_chatGPT(user_input+ " (mô tả chi tiết giúp tôi)")
            dispatcher.utter_message(text=res)
    
        return []