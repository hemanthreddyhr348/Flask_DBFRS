from flask import Flask
app = Flask(__name__)
#@app.route('/')
@app.route('/get_recomended_items/<item_in>')

def get_recomended_items(item_in):
    import pandas as pd
    import matplotlib.pyplot as plt
    import nltk
    from nltk.tokenize import RegexpTokenizer
    tokenizer = RegexpTokenizer(r'\w+')
    pd.set_option('display.max_rows', None)
    df1 = pd.read_csv('timhortons.csv', encoding= 'unicode_escape')
    df2 = pd.read_csv('subway.csv', encoding= 'unicode_escape')
    df3 = df1.append(df2, ignore_index=True)
    df4 = pd.read_csv('mcdonalds.csv', encoding= 'unicode_escape')
    df =  df3.append(df4, ignore_index=True)
    #conversion of all the text data into upper case to make our life easier.
    df = df.apply(lambda x: x.astype(str).str.upper())
    #keyword addition from name of the column
    item_name_column = df['Menu Item']
    def get_key_words_from_column(name_column):
        keywords = []
        for i in name_column:
            keywords.append(tokenizer.tokenize(i))
        return keywords
    Keywords_col = pd.Series(get_key_words_from_column(item_name_column))
    df['Keywords'] = Keywords_col
    # extracting keywords from columns and appending it to the keyword column
    columns = df.columns.to_list()
    for col in columns:
        if((columns.index(col)>2) & (columns.index(col)!= columns.index("Keywords")) & (columns.index(col)!= columns.index("Keywords"))):
            #print(col)
            for i, r in df.iterrows():
                if (r[col] == 'X' or r[col] == 'O'):
                    if col.upper() not in r['Keywords']:
                        r['Keywords'].append(col.upper())
                        #print(f"{i} : {r['Keywords']}")
            #         else:
            #             #print('present')
            #             print(f"{i} : {r['Keywords']}")
            #     else:
            #         #print('present')
            #         print(f"{i} : {r['Keywords']}")
    #df[["Menu Item",'Keywords']]
    #df.head(5)
    for i, r in df.iterrows():
        if r["Item Temp"] not in r['Keywords']:
            r['Keywords'].append(r["Item Temp"])
            
    #df[["Menu Item",'Keywords']]
    df_keywords = df[['Menu Item','Keywords']]
    Keyword_dict = df_keywords.set_index('Menu Item').to_dict()['Keywords']
    #importing the main dataset and adding a column index for better referencing
    dataframe = pd.read_csv('FoodChain_1.2.csv')
    def add_index_column(df):
        index = []
        for i, r in df.iterrows():
            index.append(i)
        return index
    index_col = pd.Series(add_index_column(dataframe))
    #print(index_col)
    dataframe['Index'] = index_col
    #converting all the values into upper case
    #match and fill the dictonary values and fill them in the main dataset.
    #if no match found use the name to generate keywords and fill.
    dataframe = dataframe.apply(lambda x: x.astype(str).str.upper())
    df3 = dataframe#[dataframe['Food chain'] == 'TIM HORTONS']
    df3 = df3.assign(Keywords='NAN')
    for i, r in df3.iterrows():
        if (Keyword_dict.get(r['Menu Items'])):
            r['Keywords']=Keyword_dict.get(r['Menu Items'])
        else:
            r['Keywords']=tokenizer.tokenize(r['Menu Items'])
    #index_col
    Keyword_data = df3

    # cosine similarity application for the Tims data.

    import numpy as np
    import difflib
    from sklearn.feature_extraction.text import TfidfVectorizer
    from sklearn.metrics.pairwise import cosine_similarity
    #Load the preprocessed data.
    # Converting the data column data of keywords into a string, appending the string, drop the keyword column
    Keyword_data['Keywords_str'] = [' '.join(map(str, l)) for l in Keyword_data['Keywords']]
    Keyword_data.drop(['Keywords'], axis=1)
    #Done with the keyword addition in string format
    # Defining the features
    features = ['Food chain', "Menu Category", "Keywords_str"]
    #Replacing the missing null values in all the four columns
    for f in features:
        #print(type(f))
        Keyword_data[f] = Keyword_data[f].fillna(".")
    # concatinating all the features from the dataset items into a single string.
    total_features = Keyword_data['Food chain']+" "+ Keyword_data["Menu Category"]+ " "+Keyword_data["Keywords_str"]
    # TF-IDF 
    vectorizer = TfidfVectorizer()
    feature_vectors = vectorizer.fit_transform(total_features)
    #Cosine_similarity score
    similarity = cosine_similarity(feature_vectors)
    #Get the item name from the user
    item = item_in.upper() #input("enter the item name: ")
    #this is a dummy text
    list_of_items = Keyword_data["Menu Items"].tolist()
    item_close_match = difflib.get_close_matches(item, list_of_items)
    index_of_the_item = Keyword_data[Keyword_data["Menu Items"] == item_close_match[0]]['Index'].values[0]
    #getting the list of similar movies
    Keyword_data[Keyword_data['Index'] == index_of_the_item].index
    similarity_score = list(enumerate(similarity[int(index_of_the_item)]))
    #print(similarity_score)
    #plotting of the similarity scores
    
    #%matplotlib inline
    x=[]
    y=[]
    for i in similarity_score:
        x.append(i[0])
        y.append(i[1])
    y
    # # Graph 1
    # data_for_plot = Keyword_data
    # fig, axes = plt.subplots(nrows=1,ncols=1)
    # axes.bar(x,y,color='green')
    # #axes.bar(x,y=1,marker="*")
    # # Graph 2
    # data_for_plot['Similarity_Score']= y
    # data_for_plot
    # import plotly.express as px
    # #data_canada = px.data.gapminder().query("country == 'Canada'")
    # type(similarity_score)
    # fig = px.bar(data_for_plot, x='Index', y='Similarity_Score',hover_name="Menu Items",hover_data=["Food chain","Energy (kCal)"],title="Similarity_scores")
    # fig.show()
    # Sorting the dataset
    sorted_items_based_on_liking = sorted(similarity_score, key = lambda x:x[1], reverse = True) 

    title = []
    for i in sorted_items_based_on_liking:
        index = i[0]
        #print(index)
        title.append(Keyword_data[Keyword_data['Index'] == index]["Menu Items"])
        
    output_dataframe = pd.DataFrame(columns=Keyword_data.columns)
    #print(output_dataframe)
    for i in sorted_items_based_on_liking:
        #print(type(i[0]))    
        #pd.concat(output_dataframe,Keyword_data[Keyword_data["Index"] == str(i[0])])
        output_dataframe = output_dataframe.append(Keyword_data[Keyword_data["Index"] == str(i[0])])
        #print(Keyword_data[Keyword_data["Index"] == str(i[0])])
    output_dataframe = output_dataframe.transpose()
    output_json = output_dataframe.to_json()
    import json
    Data = json.loads(output_json)
    Data_arr = []
    for key, value in Data.items():
        print(key)
        # temp_dict = {}
        # temp_dict[key] = value
        # Data_arr.append(temp_dict)
        Data_arr.append(value)
    return Data_arr


@app.route('/Total_items/')

def Total_items():
    import pandas as pd
    df = pd.read_csv('FoodChain_1.2.csv')
    df = df.transpose()
    json_data = df.to_json()
    import json
    items_Data = json.loads(json_data)
    items_Data_arr = []
    for key, value in items_Data.items():
        print(key)
        # temp_dict = {}
        # temp_dict[key] = value
        # items_Data_arr.append(temp_dict)
        items_Data_arr.append(value)
    return items_Data_arr

if __name__== '__main__':
    app.run(host="0.0.0.0",port=5000)