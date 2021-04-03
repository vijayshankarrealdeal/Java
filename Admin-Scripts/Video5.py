import firebase_admin
from firebase_admin import credentials,firestore
from firebase_admin import storage
cred = credentials.Certificate("./adminKey.json")
firebase_admin.initialize_app(cred, {
    'storageBucket': 'women-e598c.appspot.com'
})
#Database Methods
db = firestore.client()

discrip = ""


title = "Interview1"
cloudStorageLink = "https://firebasestorage.googleapis.com/v0/b/women-e598c.appspot.com/o/y2mate.com%20-%20Accenture%20CEOs%20Advice%20To%20Women%20Stand%20Out%20%20Forbes_480p.mp4?alt=media&token=af72d803-a096-4490-9814-0f90b44a80b4"
name = ""
source = ""
sourceLink = ""
discription = discrip
viewsOnVideo = 19,21,167
socialHandle = ""
webpage = ""

if(len(title)!=0 and len(cloudStorageLink)!=0):
    videsoWrite =  db.collection("adminContent").document("Videos").collection("data").document().set({
        "title":title,
        "name":name,
        "source":source,
        "sourceLink":sourceLink,
        "discription":discription,
        "viewsOnVideo":viewsOnVideo,
        "socialHandle":socialHandle,
        "webpage":webpage,
        "cloudStorageLink":cloudStorageLink
    }) 
else:
    print("Error")
